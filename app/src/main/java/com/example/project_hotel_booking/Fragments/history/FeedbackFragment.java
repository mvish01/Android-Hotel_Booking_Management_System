package com.example.project_hotel_booking.Fragments.history;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.utils.API;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {

    private EditText feedbackEditText;
    private Button submitButton;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedbackEditText = rootView.findViewById(R.id.feedbackEditText);
        submitButton = rootView.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return rootView;
    }

    private void submitFeedback() {
        String feedbackText = feedbackEditText.getText().toString();

        if (feedbackText.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get user ID and reservation ID from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("project", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);
        int reservationId = sharedPreferences.getInt("selected_reservation_id", -1);

        JsonObject feedbackData = new JsonObject();
        feedbackData.addProperty("user_id", userId);
        feedbackData.addProperty("reservation_id", reservationId);
        feedbackData.addProperty("feedback_text", feedbackText);

        API api = RetrofitClient.getInstance().getApi();
        Call<JsonObject> call = api.postFeedback(feedbackData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Feedback posted successfully
                    Toast.makeText(requireContext(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error response
                    Toast.makeText(requireContext(), "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle failure
                Toast.makeText(requireContext(), "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
