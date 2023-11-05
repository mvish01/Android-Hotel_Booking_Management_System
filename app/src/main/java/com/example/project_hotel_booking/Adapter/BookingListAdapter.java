package com.example.project_hotel_booking.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_hotel_booking.Fragments.history.BookingDetailFragment;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.entity.Reservation;
import com.example.project_hotel_booking.entity.Room;

import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder>  {

    private Context context;
    private List<Reservation> bookingItemList;


    public BookingListAdapter(Context context, List<Reservation> bookingItemList) {
        this.context = context;
        this.bookingItemList = bookingItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation bookingItem = bookingItemList.get(position);
        Room room = bookingItem.getRoom();

        if (room != null) {
            holder.textRoomType.setText(room.getRoomType());
        } else {
            holder.textRoomType.setText("Unknown Room Type");
        }
        holder.textRoomNumber.setText("Room Number: " + room.getRoomNumber());
        Glide.with(context).load("http://192.168.5.110:4000/"+room.getImages()).into(holder.roomImageView);

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int reservationId = bookingItem.getReservationId(); // Get the reservation ID

                // Save the reservation ID to SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("project", MODE_PRIVATE);
                sharedPreferences.edit().putInt("selected_reservation_id", reservationId).apply();

                // Replace Fragment2 with BookingDetailFragment
                BookingDetailFragment bookingDetailFragment = new BookingDetailFragment();



                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_confirm_list, bookingDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                // Set the visibility of frag2 to invisible
                View fragmentLLoffrag2 = ((FragmentActivity) context).findViewById(R.id.your_linear_layout_id);
                if (fragmentLLoffrag2 != null) {
                    fragmentLLoffrag2.setVisibility(View.INVISIBLE);
                }

            }
        });

    }



    @Override
    public int getItemCount() {
        return bookingItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textRoomType, textRoomNumber;
        Button btn_detail;
        LinearLayout linearLayout;
        ImageView roomImageView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);


            textRoomNumber = itemView.findViewById(R.id.textRoomNumber);
            textRoomType = itemView.findViewById(R.id.textRoomType);
            linearLayout = itemView.findViewById(R.id.your_linear_layout_id);
            roomImageView = itemView.findViewById(R.id.image);
            btn_detail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
