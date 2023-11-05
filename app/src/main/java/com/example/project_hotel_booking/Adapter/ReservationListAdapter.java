package com.example.project_hotel_booking.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_hotel_booking.Fragments.booking_list.ReservationList;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.Confirmation;
import com.example.project_hotel_booking.entity.Reservation;
import com.example.project_hotel_booking.entity.Room;
import com.example.project_hotel_booking.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder>
{
    private List<Reservation> reservataionList;
    private Context context;
    private SharedPreferences sharedPreferences;
//    private BookingCancellationListener cancellationListener;

    public  ReservationListAdapter(Context context, List<Reservation> reservataionList)
    {
        this.context = context;
        this.reservataionList= reservataionList;
    }

//   public void setCancellationListener(BookingCancellationListener listener) {
//    this.cancellationListener = listener;
//}

    @NonNull
    @Override
    public ReservationListAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.reservation_list,parent,false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationListAdapter.ReservationViewHolder holder, int position)
    {
        Reservation reservation = reservataionList.get(position);

        if (reservation != null && reservation.getRoom() != null)
        {


        holder.textReservationName.setText(""+reservation.getRoom().getRoomType());

        holder.textCheckInDate.setText("Check-in: " + reservation.getCheckInDate());
        holder.textCheckOutDate.setText("Check-out: " + reservation.getCheckOutDate());

        int confirmation_id = reservation.getConfirmation().getConfirmationId();
        Glide.with(context).load("http://192.168.5.110:4000/"+reservation.getRoom().getImages()).into(holder.image);

        holder.buttonCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RetrofitClient.getInstance().getApi().cancelReservation(confirmation_id).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JsonObject responseBody = response.body();
                            Log.e("Response Body", responseBody.toString());

                            if (responseBody.get("status").getAsString().equals("success")) {
                                // Find the index of the reservation to remove
                                int positionToRemove = -1;
                                for (int i = 0; i < reservataionList.size(); i++) {
                                    if (reservataionList.get(i).getConfirmation().getConfirmationId() == confirmation_id) {
                                        positionToRemove = i;
                                        break;
                                    }
                                }

                                if (positionToRemove != -1) {
                                    reservataionList.remove(positionToRemove);
                                    notifyItemRemoved(positionToRemove);
                                    Toast.makeText(context, "Reservation cancelled successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Reservation not found in the list", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Reservation cancellation failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(context, "room not deleted", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        }
        else {
            Log.e("ReservationListAdapter", "Null reservation or room object at position: " + position);
        }
    }

    @Override
    public int getItemCount()
    {
        return reservataionList.size();
    }
    public class ReservationViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textReservationName;

        TextView textCheckInDate;
        TextView textCheckOutDate;
        Button buttonCancelBooking;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewreservationlist);
            textReservationName = itemView.findViewById(R.id.textViewreservationlist);

            textCheckInDate = itemView.findViewById(R.id.textViewCheckInDate);
            textCheckOutDate = itemView.findViewById(R.id.textViewCheckOutDate);
            buttonCancelBooking = itemView.findViewById(R.id.buttonCancelBooking);

        }
    }

}
