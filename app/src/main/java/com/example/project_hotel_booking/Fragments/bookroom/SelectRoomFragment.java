package com.example.project_hotel_booking.Fragments.bookroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_hotel_booking.Adapter.RoomListAdapter;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.activity.Select_Room;
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

public class SelectRoomFragment extends Fragment {

    private RecyclerView recyclerView;
    private RoomListAdapter roomListAdapter;
    private List<Room> roomList;

    public SelectRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_room, container, false);

        roomList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        roomListAdapter = new RoomListAdapter(getActivity(), roomList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(roomListAdapter);

        getAllRooms();

        return rootView;
    }

    private void getAllRooms() {
        RetrofitClient.getInstance().getApi().getAllRooms().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                for (JsonElement element : jsonArray) {
                    Room room = new Room();

                    room.setRoomNumber(element.getAsJsonObject().get("room_number").getAsInt());
                    room.setRoomType(element.getAsJsonObject().get("room_type").getAsString());
                    room.setImages(element.getAsJsonObject().get("images").getAsString());

                    roomList.add(room);
                }
                roomListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle failure here
//                Toast.makeText(Select_RoomFragment.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
