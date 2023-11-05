package com.example.project_hotel_booking.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project_hotel_booking.Adapter.RoomListAdapter;
import com.example.project_hotel_booking.R;
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

public class Select_Room extends AppCompatActivity {
    RecyclerView recyclerView;
    RoomListAdapter roomListAdapter;
    List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        roomList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        roomListAdapter = new RoomListAdapter( this, roomList);


        recyclerView.setLayoutManager(new GridLayoutManager(Select_Room.this,1));
        recyclerView.setAdapter(roomListAdapter);
        getAllRooms();
    }

    private void getAllRooms() {
        RetrofitClient.getInstance().getApi().getAllRooms().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                System.out.println("json array:"+jsonArray);

                for (JsonElement element :jsonArray) {
                    Room room= new Room();

                    room.setRoomNumber(element.getAsJsonObject().get("room_number").getAsInt());
                    room.setRoomType(element.getAsJsonObject().get("room_type").getAsString());
                    room.setImages(element.getAsJsonObject().get("images").getAsString());
                      System.out.println(element.getAsJsonObject().get("images").getAsString());
                    roomList.add(room);

                }
                System.out.println(roomList);
                   roomListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Select_Room.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}