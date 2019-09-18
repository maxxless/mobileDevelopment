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

import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Auth {

    private final static int RC_SIGN_IN = 2;
    private static GoogleSignInClient googleSignInClient;
    private static CallbackManager callbackManager;

    static void signIn(String email, String password, final Activity activity) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //if login success
                    Toast.makeText(getApplicationContext(), "Signed in successfully", Toast.LENGTH_SHORT).show();
                    auth.getCurrentUser();
                    startMainActivity(activity);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static void signUp(String email, String password, final Activity activity) {
        //register user with Firebase
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Signed up successfully.", Toast.LENGTH_SHORT).show();
                    sendSignUpConfirm(Objects.requireNonNull(auth.getCurrentUser()));
                    startMainActivity(activity);
                } else {
                    Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                    //currentUser = null;
                }
            }
        });
    }

    private static void sendSignUpConfirm(FirebaseUser user) {
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

    public static void signOut() {
        if (googleSignInClient != null) {
            googleSignInClient.revokeAccess();
        }

        LoginManager.getInstance().logOut();

        FirebaseAuth.getInstance().signOut();
    }

    static void facebookSignIn(final Activity activity) {
        //Facebook
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

    //configure Facebook
    private static void handleFacebookToken(AccessToken accessToken, final Activity activity) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getApplicationContext(), "Authentication is successful.(Firebase)",
                            Toast.LENGTH_SHORT).show();
                    auth.getCurrentUser();
                    startMainActivity(activity);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Authentication failed.(Firebase)",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static void googleSignIn(Activity activity) {
        GoogleSignInClient googleSignInClient = getGoogleClient();

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    static void sendRecoverCode(String email) {
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

    private static void firebaseAuthWithGoogle(GoogleSignInAccount acct,
                                               final Activity activity) {
        //connecting to firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Google authentication is successful", Toast.LENGTH_SHORT).show();
                            auth.getCurrentUser();
                            startMainActivity(activity);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Google authentication failed(Firebase part)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    static void onActivityResult(int requestCode, int resultCode, Intent data,
                                        Activity activity) {
        //facebook callback
        getCallbackManager().onActivityResult(requestCode, resultCode, data);

        //google responce
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Auth.firebaseAuthWithGoogle(Objects.requireNonNull(account), activity);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(), "Google authentication failed(Google part)", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    static boolean isUserAuth() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private static GoogleSignInClient getGoogleClient() {
        if (googleSignInClient == null) {
            //Google
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getApplicationContext().getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        }
        return googleSignInClient;
    }

    private static CallbackManager getCallbackManager() {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
        return callbackManager;
    }

    private static void startMainActivity(Activity activity) {
        ((AuthenticationActivity) activity).startMainActivity();
    }
}
