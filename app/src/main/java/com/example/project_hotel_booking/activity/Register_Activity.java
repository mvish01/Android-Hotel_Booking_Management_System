package com.example.project_hotel_booking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.User;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonObject;

import java.io.Console;
import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName,editTextEmail,editTextPhoneNumber,editTextPassword, editTextConfirmPassword;
    Button buttonRegister;
    Button btnGoToLogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reidter);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword =findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnGoToLogin= findViewById(R.id.btnLogin);
        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)

            {
                System.out.println("inside on click of register");
                User user = ValidUser();
                if(user != null && user.getFirstName() != null)
                {
                    System.out.println("inside 1st if on clickclick of register");
                    System.out.println(user.getFirstName());
                    RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                            {
                                System.out.println(user);
                                System.out.println("inside 2nd if on clickclick of register");

                                Toast.makeText(Register_Activity.this, "user registered succefully", Toast.LENGTH_SHORT).show();
//                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(Register_Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            private User ValidUser()
            {
                String password=editTextPassword.getText().toString();
                String confirmpassword = editTextConfirmPassword.getText().toString();

              if (editTextFirstName.getText().toString().isEmpty() || editTextLastName.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() || editTextPhoneNumber.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Register_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return null;
                }

                BigInteger phoneNumber;
                try {
                    phoneNumber = new BigInteger(editTextPhoneNumber.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Register_Activity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return null;
                }

                if(password.equals(confirmpassword))
                {
                    User user = new User();
                    user.setFirstName(editTextFirstName.getText().toString());
                    user.setLastName(editTextLastName.getText().toString());
                    user.setEmail(editTextEmail.getText().toString());
                    user.setPhoneNumber(phoneNumber);;
                    user.setPassword(password);
                    return user;
                }
                else
                {
                    Toast.makeText(Register_Activity.this, "password does not match", Toast.LENGTH_SHORT).show();
                    return null;
                }

            }
        });

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                finish(); // Optional: Finish the current activity
            }
        });
    }
}