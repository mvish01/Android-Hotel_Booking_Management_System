package com.example.project_hotel_booking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.User;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    EditText editUserName,editPassword;
    Button login_button;
    TextView regster_here_button;
    CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName= findViewById(R.id.editUsername);
        editPassword=findViewById(R.id.editPassword);

        login_button = findViewById(R.id.btnLogin);
        regster_here_button = findViewById(R.id.register_here);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user =new User() ;
                user.setEmail(editUserName.getText().toString());
                user.setPassword(editPassword.getText().toString());
                System.out.println(user);


                if (checkBoxRememberMe.isChecked())
                    getSharedPreferences("project", MODE_PRIVATE).edit().putBoolean("login_status", true).apply();

                RetrofitClient.getInstance().getApi().loginUser(user).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        System.out.println("inside on responce");

                        if (response.isSuccessful() && response.body() != null) {
                            JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                            System.out.println("inside 1st if");
                            if (array.size() != 0) {
                                System.out.println("inside 2nd if");
                                JsonObject object = array.get(0).getAsJsonObject();


                                User user = new User();
                                user.setEmail(editUserName.getText().toString());
                                user.setPassword(editPassword.getText().toString());
                                // Store user ID

                                String firstName = object.get("firstName").getAsString();
                                String phoneNumber = object.get("phoneNumber").getAsString();
                                String email = object.get("email").getAsString();

                                int userId = object.get("user_id").getAsInt();
                                getSharedPreferences("project", MODE_PRIVATE).edit().putInt("user_id", userId).apply();

                                SharedPreferences sharedPreferences = getSharedPreferences("project", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("name", firstName);
                                editor.putString("phone_number", phoneNumber);
                                editor.putString("email",email);

                                editor.apply();

                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                startActivity(intent); // Start the Select_Room activity

                                finish();
                            }
                            else
                            {
                                Toast.makeText(Login_Activity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login_Activity.this, "Something Went Wrong json obj", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(Login_Activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });

        regster_here_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,Register_Activity.class));
            }
        });

    }

}