package com.example.project_hotel_booking.Fragments.bookroom;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_hotel_booking.Fragments.bookroom.ConfirmationMessageFragment;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.activity.MainActivity;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationFragment extends Fragment {

    private TextView nameConfirmationTextView;
    private TextView emailConfirmationTextView;
    private TextView phoneConfirmationTextView;
    private TextView roomDetailsTextView;
    private TextView checkInDateTextView;
    private TextView checkOutDateTextView;
    private Button confirm_button;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        nameConfirmationTextView = view.findViewById(R.id.nameConfimation);
        emailConfirmationTextView = view.findViewById(R.id.emailConfirmation);
        phoneConfirmationTextView = view.findViewById(R.id.phoneConfirmation);
        roomDetailsTextView = view.findViewById(R.id.RoomDetails);
        checkInDateTextView = view.findViewById(R.id.checkInDate);
        checkOutDateTextView = view.findViewById(R.id.checkOutDate);
        confirm_button = view.findViewById(R.id.confirmButton);

        // Retrieve data from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("project", requireContext().MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phoneNumber = sharedPreferences.getString("phone_number", "");
        int selectedRoomNumber = sharedPreferences.getInt("selected_room_number", 0);
        String selectedRoomType = sharedPreferences.getString("selected_room_type", "");
        String checkInDate = sharedPreferences.getString("check_in_date", "");
        String checkOutDate = sharedPreferences.getString("check_out_date", "");
        int userId = sharedPreferences.getInt("user_id", 0);

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

                            // Hide the current fragment
                            getView().setVisibility(View.INVISIBLE);

                            // Replace the current fragment with the ConfirmationMessageFragment
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            ConfirmationMessageFragment messageFragment = new ConfirmationMessageFragment();
                            fragmentTransaction.replace(R.id.containerFrameLayout, messageFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
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

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    // Hide the current fragment
                    getView().setVisibility(View.INVISIBLE);


                }
                return false;
            }
        });
        return view;
    }


}