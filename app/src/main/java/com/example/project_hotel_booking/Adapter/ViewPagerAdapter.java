package com.example.project_hotel_booking.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_hotel_booking.Fragments.booking_list.ReservationList;
import com.example.project_hotel_booking.Fragments.bookroom.Fragment1;
import com.example.project_hotel_booking.Fragments.history.Fragment2;
import com.example.project_hotel_booking.Fragments.password.Fragment3;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Fragment2();
            case 2:
                return new ReservationList();
            case 3:
                return new Fragment3();
            default:
                return new Fragment1(); // Default fragment
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
