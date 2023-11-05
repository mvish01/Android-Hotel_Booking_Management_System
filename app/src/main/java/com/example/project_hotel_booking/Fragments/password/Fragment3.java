package com.example.project_hotel_booking.Fragments.password;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment {

    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangePasswordClick();
            }
            });

        return view;
    }

    private void handleChangePasswordClick() {
        String oldPassword = etOldPassword.getText().toString();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("user_id", -1);

        Log.d("User ID", "User ID retrieved from SharedPreferences: " + user_id);

        RetrofitClient.getInstance().getApi().RetriveUser(user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() ) {

                    JsonObject jsonObject = response.body().getAsJsonObject("data");

                    Log.d("User ID", "User obj retrieved from DB: " + jsonObject);

                    if (jsonObject != null && jsonObject.has("password")) {
                        String storedPassword = jsonObject.get("password").getAsString();

                        if (oldPassword.equals(storedPassword))
                        {
                            String newPassword = etNewPassword.getText().toString();
                            String confirmPassword = etConfirmPassword.getText().toString();




                            JsonObject password = new JsonObject();


                            password.addProperty("password", newPassword);




                            // Validate passwords and perform change password logic here
                            if (newPassword.equals(confirmPassword)) {
                               changePassword(password ,user_id);
                                Toast.makeText(getContext(), "chahe pass method getting called", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Incorrect old password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Password data not found in response", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            }

            private void changePassword(JsonObject password,  int user_id) {
                Log.d("User_Id","user id in chane passord "+user_id);
                Log.d("User_Id","password in chane passord "+password);


                RetrofitClient.getInstance().getApi().UpdatePassword(password, user_id).enqueue(new Callback<JsonObject>()
                {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("Res",""+response.body());

                        if (response.isSuccessful())
                        {

                                    Toast.makeText(getContext(), "Operation successful rows affected", Toast.LENGTH_SHORT).show();

                        }

                        else
                        {
                            Toast.makeText(getContext(), "responce unsuccessfull in update password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "Fail to update password", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Failed to get user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
