package com.example.project_hotel_booking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import com.example.project_hotel_booking.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {
    TextView nameConfirmationTextView ;
    String nameConfirmation ;

    TextView emailConfirmationTextView ;
    String emailConfirmation;

    TextView phoneConfirmationTextView ;
    String phoneConfirmation ;

    TextView roomDetailsTextView ;
    String roomDetails ;
    TextView checkInDateTextView;
    TextView checkOutDateTextView;

    Button confirm_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        nameConfirmationTextView = findViewById(R.id.nameConfimation);
        emailConfirmationTextView = findViewById(R.id.emailConfirmation);
        phoneConfirmationTextView = findViewById(R.id.phoneConfirmation);
        roomDetailsTextView = findViewById(R.id.RoomDetails);
        checkInDateTextView = findViewById(R.id.checkInDate);
        checkOutDateTextView = findViewById(R.id.checkOutDate);
        confirm_button = findViewById(R.id.confirmButton);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("project", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phoneNumber = sharedPreferences.getString("phone_number", "");
        int selectedRoomNumber = sharedPreferences.getInt("selected_room_number", 0);
        String selectedRoomType = sharedPreferences.getString("selected_room_type", "");
        String checkInDate = sharedPreferences.getString("check_in_date", ""); // Retrieve check-in date
        String checkOutDate = sharedPreferences.getString("check_out_date", ""); // Retrieve check-out date
        int userId = sharedPreferences.getInt("user_id", 0); // Retrieve userId

        // Update the TextViews with retrieved data
        nameConfirmationTextView.setText("Name: " + name);
        emailConfirmationTextView.setText("Email: " + email);
        phoneConfirmationTextView.setText("Phone: " + phoneNumber);

        // Update the room details TextView
        String roomDetails = "Room Number: " + selectedRoomNumber + "\nRoom Type: " + selectedRoomType;
        roomDetailsTextView.setText(roomDetails);

        // Update the check-in and check-out date TextViews
        checkInDateTextView.setText("Check-in Date: " + checkInDate);
        checkOutDateTextView.setText("Check-out Date: " + checkOutDate);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrofit call to book a reservation
                JsonObject reservationData = new JsonObject();
                reservationData.addProperty("userId", userId);
                reservationData.addProperty("roomId", selectedRoomNumber);
                reservationData.addProperty("checkInDate", checkInDate);
                reservationData.addProperty("checkOutDate", checkOutDate);

                Call<JsonObject> call = RetrofitClient.getInstance().getApi().bookReservation(reservationData);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            // Reservation successful, show a success message or perform further actions
                            Snackbar.make(v, "Reservation Successful!", Snackbar.LENGTH_LONG).show();
                        } else {
                            // Reservation failed, show an error message or perform error handling
                            Snackbar.make(v, "Reservation Failed. Please try again.", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        // Handle network or other failures
                        Snackbar.make(v, "Reservation Failed. Please check your network connection.", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });
    }


}