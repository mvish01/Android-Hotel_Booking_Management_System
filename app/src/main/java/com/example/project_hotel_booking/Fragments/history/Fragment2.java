package com.example.project_hotel_booking.Fragments.history;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_hotel_booking.Adapter.BookingListAdapter;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.Confirmation;
import com.example.project_hotel_booking.entity.Reservation;
import com.example.project_hotel_booking.entity.Room;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment2 extends Fragment {

    private RecyclerView recyclerView;
    private BookingListAdapter bookingListAdapter;
    List<Reservation> bookingItemList;
    private TextView noConfirmedBookingsTextView;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_2, container, false);

        noConfirmedBookingsTextView = rootView.findViewById(R.id.noConfirmedBookingsTextView);

        bookingItemList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclearViewFragment2);
        bookingListAdapter = new BookingListAdapter(getContext(), bookingItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingListAdapter);
        getBookings();
        return rootView;
    }

    private void getBookings() {
        // Get the context of the Fragment
        Context context = getContext();

        // Check if the context is not null before proceeding
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("project", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("user_id", -1);
            int selectedReservationId = sharedPreferences.getInt("selected_reservation_id", -1);
            RetrofitClient.getInstance().getApi().getUserReservationDetail(userId).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
                {
                    if (response.body() == null) {
                        Toast.makeText(context, "u dont have any confirm bookings", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                        if (jsonArray == null || jsonArray.size() == 0) {
                            // No confirmed bookings
                            recyclerView.setVisibility(View.GONE);
                            noConfirmedBookingsTextView.setVisibility(View.VISIBLE);
                        } else {
                            // Confirmed bookings available
                            recyclerView.setVisibility(View.VISIBLE);
                            noConfirmedBookingsTextView.setVisibility(View.GONE);

                            // Inside your loop where you are parsing the JSON response
                            for (JsonElement element : jsonArray) {
                                Reservation reservation = new Reservation();
                                reservation.setReservationId(element.getAsJsonObject().get("reservation_id").getAsInt());
                                reservation.setCheckInDate(element.getAsJsonObject().get("check_in_date").getAsString());
                                reservation.setCheckOutDate(element.getAsJsonObject().get("check_out_date").getAsString());

                                Room room = new Room();
                                room.setRoomType(element.getAsJsonObject().get("room_type").getAsString());
                                room.setRoomNumber(element.getAsJsonObject().get("room_number").getAsInt());

                                // Extract the image name and set it in the room object
                                room.setImages(element.getAsJsonObject().get("images").getAsString());

                                reservation.setRoom(room);

                                Confirmation confirmation = new Confirmation();
                                confirmation.setConfirmationId(element.getAsJsonObject().get("confirmation_id").getAsInt());
                                confirmation.setAmount(element.getAsJsonObject().get("amount").getAsFloat());
                                confirmation.setPaymentDate(element.getAsJsonObject().get("payment_date").getAsString());

                                reservation.setConfirmation(confirmation);

                                bookingItemList.add(reservation);
                            }

                        }
                        bookingListAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }



            });
        }
    }

}