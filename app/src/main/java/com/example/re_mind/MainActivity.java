package com.example.re_mind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    ArrayList<ReMinder> listaReMinderow = new ArrayList<ReMinder>();
    EditText nazwaRemindera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nazwaRemindera = findViewById(R.id.runningReminder);

        NotificationChannel channel = new NotificationChannel("ReMinder", "ReMinder", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    public void launchAdd(View v) {
        Intent i = new Intent(this, Add.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 1 && data != null) {
            ReMinder dodanyReminder = new ReMinder();
            dodanyReminder.setDayOfMonth(data.getIntExtra("dayOfMonth", 1));
            dodanyReminder.setYear(data.getIntExtra("year", 2020));
            dodanyReminder.setMonth(data.getIntExtra("month", 1));
            dodanyReminder.setMinute(data.getIntExtra("minute", 1));
            dodanyReminder.setHour(data.getIntExtra("hour", 1));
            dodanyReminder.setName(data.getStringExtra("name"));
            String reminderTime = Integer.toString(dodanyReminder.year) + "-" + String.format("%02d", dodanyReminder.month) + "-" + String.format("%02d", dodanyReminder.dayOfMonth) + "T" + String.format("%02d", dodanyReminder.hour) + ":" + String.format("%02d", dodanyReminder.minute) + ":00";
            listaReMinderow.add(dodanyReminder);
            nazwaRemindera.setText(dodanyReminder.name);

            //setAlarm
            long alarmTimeInMillis = LocalDateTime.parse(reminderTime).atZone(ZoneId.of("Europe/Oslo")).toInstant().toEpochMilli();
            Intent intent = new Intent(MainActivity.this, NotificationHelper.class);
            intent.putExtra("tekst notyfikacji", dodanyReminder.name);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
/* no longer needed
    public void sendNotification(String notificationText){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "ReMinder");
        builder.setContentTitle("Re-Minder:");
        builder.setContentText(notificationText);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
    }

} */