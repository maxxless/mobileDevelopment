package com.leshchyshyn.mobileapp.auth;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.utils.SharedPrefsHelper;

import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Auth {
    private static Auth INSTANCE = new Auth();

    private static final int REQUEST_CODE_SIGN_IN = 2;
    private GoogleSignInClient googleSignInClient;

    private CallbackManager callbackManager;

    private SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();

    private Auth() {

    }

    public static Auth getInstance() {
        return INSTANCE;
    }

    private void startMainActivity(Activity activity) {
        ((AuthenticationActivity) activity).startMainActivity();
    }

    public void signIn(final String email, final String password, final Activity activity) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Signed in successfully", Toast.LENGTH_SHORT).show();
                    auth.getCurrentUser();
                    startMainActivity(activity);
                } else {
                    Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUp(final String email, final String password, final String username, final Activity activity) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sharedPrefsHelper.saveUsername(username);
                    Toast.makeText(getApplicationContext(), "Signed up successfully.", Toast.LENGTH_SHORT).show();
                    sendSignUpConfirm(Objects.requireNonNull(auth.getCurrentUser()));
                    startMainActivity(activity);
                } else {
                    Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendSignUpConfirm(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email was sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email was not sent: error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendRecoveryCode(final String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Email was sent successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email was not sent: error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isUserAuth() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void signOut() {
        sharedPrefsHelper.clearPrefs();

        if (googleSignInClient != null) {
            googleSignInClient.revokeAccess();
        }

        LoginManager.getInstance().logOut();

        FirebaseAuth.getInstance().signOut();
    }


    /**
     * Facebook part
     */
    public void facebookSignIn(final Activity activity) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        getCallbackManager();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken(), activity);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "User cancelled Facebook login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private CallbackManager getCallbackManager() {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
        return callbackManager;
    }

    private void handleFacebookToken(final AccessToken accessToken, final Activity activity) {
        final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sharedPrefsHelper.saveUsername(Objects.requireNonNull(auth.getCurrentUser()).getDisplayName());
                    Toast.makeText(getApplicationContext(), "Authentication is successful.(Firebase)",
                            Toast.LENGTH_SHORT).show();
                    auth.getCurrentUser();
                    startMainActivity(activity);
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication failed.(Firebase)",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Google part
     */
    public void googleSignIn(Activity activity) {
        GoogleSignInClient googleClient = getGoogleClient();

        Intent signInIntent = googleClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    private GoogleSignInClient getGoogleClient() {
        if (googleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getApplicationContext().getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        }
        return googleSignInClient;
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct,
                                        final Activity activity) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sharedPrefsHelper.saveUsername(acct.getDisplayName());
                            Toast.makeText(getApplicationContext(), "Google authentication is successful", Toast.LENGTH_SHORT).show();
                            auth.getCurrentUser();
                            startMainActivity(activity);
                        } else {
                            Toast.makeText(getApplicationContext(), "Google authentication failed(Firebase part)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Request code checking
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data,
                                 Activity activity) {
        getCallbackManager().onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Auth.getInstance().firebaseAuthWithGoogle(Objects.requireNonNull(account), activity);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Google authentication failed(Google part)", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
