package com.example.project_hotel_booking.Fragments.bookroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_hotel_booking.R;

public class Fragment1 extends Fragment {



    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_1, container, false);

        // Replace the container with the SelectRoomFragment
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove the previous fragments from the back stack before adding a new one
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Add the SelectRoomFragment to the container and to the back stack
        fragmentTransaction.replace(R.id.containerFrameLayout, new SelectRoomFragment());
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        return rootView;
    }
}