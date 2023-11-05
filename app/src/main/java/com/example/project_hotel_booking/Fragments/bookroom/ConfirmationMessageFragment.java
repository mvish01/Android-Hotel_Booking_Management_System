package com.example.project_hotel_booking.Fragments.bookroom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_hotel_booking.R;


public class ConfirmationMessageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmation_message, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    // Create an instance of the ConfirmationFragment
                    ConfirmationFragment confirmationFragment = new ConfirmationFragment();

                    // Start a new fragment transaction
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                    // Replace the current fragment with ConfirmationFragment
                    transaction.replace(R.id.containerFrameLayout, confirmationFragment);

                    // Remove the current fragment from the back stack
                    getParentFragmentManager().popBackStack();

                    // Commit the transaction
                    transaction.commit();

                    return true;
                }
                return false;
            }


        });
        return view;
    }


}