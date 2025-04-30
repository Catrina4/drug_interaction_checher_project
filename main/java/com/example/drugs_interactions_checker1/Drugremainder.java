package com.example.drugs_interactions_checker1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Drugremainder extends AppCompatActivity {

    private EditText etDrugName;
    private TimePicker timePicker;
    private Button btnSetReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugremainder);

        etDrugName = findViewById(R.id.etDrugName);
        timePicker = findViewById(R.id.timePicker);
        btnSetReminder = findViewById(R.id.btnSetReminder);

        timePicker.setIs24HourView(true); // 24-hour format (optional)

        btnSetReminder.setOnClickListener(v -> setDrugReminder());
    }

    private void setDrugReminder() {
        String drugName = etDrugName.getText().toString().trim();

        if (drugName.isEmpty()) {
            Toast.makeText(this, "Please enter a drug name.", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Set calendar for the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // If the time is before now, set it for the next day
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Create the intent and pending intent
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("drugName", drugName);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
            Toast.makeText(this, "Reminder set for " + drugName, Toast.LENGTH_SHORT).show();
        }
    }
}
