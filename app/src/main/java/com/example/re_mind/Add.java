package com.example.re_mind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add extends AppCompatActivity {
    int hour, minute, year, month, dayOfMonth;
    EditText timeBox;
    private CalendarView myCalendarView;
    EditText nameBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Dodaj nowy Re-Minder");
        timeBox = findViewById(R.id.timeBox);
        nameBox = findViewById(R.id.nameBox);
        myCalendarView = (CalendarView) findViewById(R.id.calendarView);
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        hour = 12;
        minute = 0;
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);



        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int setYear, int setMonth, int setDayOfMonth) {
                year = setYear;
                month = setMonth;
                dayOfMonth = setDayOfMonth;
            }
        });

    }
    public void editTime(View v){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeBox.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Wybierz czas ReMindera");
        timePickerDialog.show();
    }
    public void saveButtonOnClick(View v){
        Intent output = new Intent();
        output.putExtra("hour", hour);
        output.putExtra("minute", minute);
        output.putExtra("year", year);
        output.putExtra("month", month+1); //z jakiegos powodu zwraca liczbe 1 mniejsza niz miesiac
        output.putExtra("dayOfMonth", dayOfMonth);
        output.putExtra("name", nameBox.getText().toString());
        setResult(1, output);
        finish();
    }
    public void cancelButtonOnClick(View v){
        finish();
    }
}