package com.example.project_hotel_booking.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_hotel_booking.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Date_Activity extends AppCompatActivity {
    private TextView textCheckInDate;
    private TextView textCheckOutDate;
    private Button buttonConfirm;
    private Calendar calendar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        textCheckInDate = findViewById(R.id.text_checkin_date);
        textCheckOutDate = findViewById(R.id.text_checkout_date);
        buttonConfirm = findViewById(R.id.button_confirm);
        calendar = Calendar.getInstance();

        textCheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        textCheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save check-in and check-out dates to SharedPreferences
                sharedPreferences = getSharedPreferences("project", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putString("check_in_date", textCheckInDate.getText().toString());
                editor.putString("check_out_date", textCheckOutDate.getText().toString());
                editor.apply();

                Intent intent = new Intent(Date_Activity.this, ConfirmationActivity.class);

                startActivity(intent);

            }
        });
    }

    private void showDatePickerDialog(final boolean isCheckInDate) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (isCheckInDate) {
                            updateCheckInDate();
                        } else {
                            updateCheckOutDate();
                        }
                    }
                },
                year,
                month,
                day
        );

        // Set a minimum date if needed
         datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void updateCheckInDate() {
        Date checkInDate = calendar.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            String formattedDate = outputFormat.format(inputFormat.parse(inputFormat.format(checkInDate)));
            textCheckInDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateCheckOutDate() {
        Date checkOutDate = calendar.getTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            String formattedDate = outputFormat.format(inputFormat.parse(inputFormat.format(checkOutDate)));
            textCheckOutDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
