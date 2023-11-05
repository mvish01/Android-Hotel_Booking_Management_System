package com.example.project_hotel_booking.Fragments.booking_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_hotel_booking.Adapter.ReservationListAdapter;
import com.example.project_hotel_booking.Adapter.RoomListAdapter;
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


public class ReservationList extends Fragment {

    private RecyclerView recyclerView;
    private ReservationListAdapter reservationListAdapter;
    private List<Reservation> reservationList ;


    public ReservationList()
    {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation_list, container,false);

        reservationList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.ReservationListFragment);

        reservationListAdapter = new ReservationListAdapter(getActivity(), reservationList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(reservationListAdapter);


        getAllReservation();
        return view;
    }

    private void getAllReservation() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id",-1);
        Log.e("reservation_list",""+userId);

        RetrofitClient.getInstance().getApi().getUserReservationDetail(userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();

                    // Check if "data" field is present in the response
                    if (responseBody.has("data") && responseBody.get("data").isJsonArray()) {
                        JsonArray jsonArray = responseBody.getAsJsonArray("data");

                        if (jsonArray.size() > 0) {
                            // Clear the reservation list before adding new data
                            reservationList.clear();

                            // Parse and add reservations
                            for (JsonElement element : jsonArray) {
                                Reservation reservation = new Reservation();
                                reservation.setReservationId(element.getAsJsonObject().get("reservation_id").getAsInt());
                                reservation.setCheckInDate(element.getAsJsonObject().get("check_in_date").getAsString());
                                reservation.setCheckOutDate(element.getAsJsonObject().get("check_out_date").getAsString());

                                Room room = new Room();
                                room.setImages(element.getAsJsonObject().get("images").getAsString());
                                room.setRoomType(element.getAsJsonObject().get("room_type").getAsString());
                                reservation.setRoom(room);

                                Confirmation confirmation = new Confirmation();
                                confirmation.setConfirmationId(element.getAsJsonObject().get("confirmation_id").getAsInt());

                                reservation.setConfirmation(confirmation);

                                reservationList.add(reservation);
                            }

                            // Notify the adapter about data change
                            reservationListAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "No reservations found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid response data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Response is not successful", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "failure of retrofit", Toast.LENGTH_SHORT).show();

            }
        });
    }


}