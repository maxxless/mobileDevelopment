package com.leshchyshyn.mobileapp.main_group.car;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.leshchyshyn.mobileapp.R;
import com.leshchyshyn.mobileapp.api.ApiService;
import com.leshchyshyn.mobileapp.api.RetrofitClient;
import com.leshchyshyn.mobileapp.data.model.Car;
import com.leshchyshyn.mobileapp.utils.InternetConnection;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddCarFragment extends Fragment {
    private View view;
    private Context context;

    private EditText nameEt;
    private EditText registrationNumberEt;
    private EditText colourEt;
    private EditText typeEt;

    private ImageView carImageIv;
    private ImageView carImageChooseIv;

    private Button addCarBtn;
    private Button closeBtn;

    private StorageReference imageReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_car, container, false);
        context = getContext();

        initView();
        initListeners();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageData = Objects.requireNonNull(data).getData();
            imageReference.putFile(
                    Objects.requireNonNull(imageData))
                    .addOnSuccessListener(taskSnapshot -> placeImage());
        }
    }

    private void initView() {
        nameEt = view.findViewById(R.id.name_et);
        registrationNumberEt = view.findViewById(R.id.registration_number_et);
        colourEt = view.findViewById(R.id.colour_et);
        typeEt = view.findViewById(R.id.type_et);

        carImageIv = view.findViewById(R.id.car_photo_iv);
        carImageChooseIv = view.findViewById(R.id.car_photo_edit_iv);

        addCarBtn = view.findViewById(R.id.add_car_btn);
        closeBtn = view.findViewById(R.id.close_car_btn);

        StorageReference folderReference = FirebaseStorage.getInstance().getReference().child("folder");
        imageReference = folderReference.child(LocalDateTime.now().toString().trim() + ".jpg");
    }

    private void initListeners() {
        carImageChooseIv.setOnClickListener(view -> uploadPicture());

        closeBtn.setOnClickListener(view -> getFragmentManager().popBackStack());

        addCarBtn.setOnClickListener(view -> uploadData());
    }

    private void uploadData() {
        final String name = nameEt.getText().toString();
        final String registrationNumber = registrationNumberEt.getText().toString();
        final String colour = colourEt.getText().toString();
        final String type = typeEt.getText().toString();

        imageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            if (areFieldsValid(name, registrationNumber, type, colour)) {
                Car car = new Car(name, registrationNumber, colour, type, uri.toString());

                sendData(car);
            } else {
                showFieldsError();
            }
        });
    }

    private void uploadPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void placeImage() {
        imageReference
                .getDownloadUrl()
                .addOnSuccessListener(uri ->
                        Picasso.get().load(uri.toString()).into(carImageIv));
    }

    private void sendData(Car car) {
        if (InternetConnection.checkConnection(context)) {
            showProgress();

            ApiService api = RetrofitClient.getRetroClient();

            Call<Car> call = api.addCar(car);
            call.enqueue(new Callback<Car>() {
                @Override
                public void onResponse(Call<Car> call, Response<Car> response) {
                    hideProgress();
                    Objects.requireNonNull(getFragmentManager()).popBackStack();
                }

                @Override
                public void onFailure(Call<Car> call, Throwable t) {
                    hideProgress();
                }
            });
        } else {
            showNoInternetConnection();
        }
    }

    private void showProgress() {
        showProgressLoaderWithBackground(true, context.getString(R.string.load_data));
    }

    private void hideProgress() {
        showProgressLoaderWithBackground(false, context.getString(R.string.load_data));
    }

    private void showProgressLoaderWithBackground(boolean visibility, String text) {
        if (text == null) {
            text = "";
        }

        ((TextView) view.findViewById(R.id.progress_bar_text)).setText(text);

        if (visibility) {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.VISIBLE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            view.findViewById(R.id.container_progress_bar).setVisibility(View.GONE);
            Objects.requireNonNull(getActivity()).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void showNoInternetConnection() {
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    private void showFieldsError() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
    }

    private boolean areFieldsValid(final String name, final String registrationNumber,
                                   final String type, final String colour) {
        boolean isNameValid = !TextUtils.isEmpty(name);
        boolean isRegNumberValid = !TextUtils.isEmpty(registrationNumber);
        boolean isTypeValid = !TextUtils.isEmpty(type);
        boolean isColourValid = !TextUtils.isEmpty(colour);

        if (isNameValid) {
            nameEt.setError(context.getString(R.string.invalid_field));
        }

        if (isRegNumberValid) {
            nameEt.setError(context.getString(R.string.invalid_field));
        }

        if (isTypeValid) {
            nameEt.setError(context.getString(R.string.invalid_field));
        }

        if (isColourValid) {
            nameEt.setError(context.getString(R.string.invalid_field));
        }
        return isNameValid && isRegNumberValid && isTypeValid && isColourValid;
    }
}
