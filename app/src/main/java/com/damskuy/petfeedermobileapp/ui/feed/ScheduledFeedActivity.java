package com.damskuy.petfeedermobileapp.ui.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.data.model.Feed;

import java.util.ArrayList;
import java.util.Locale;

public class ScheduledFeedActivity extends AppCompatActivity {

    private NumberPicker npHour, npMinute, npAmPm, npServings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_feed);
        initUI();
    }

    private void initUI() {
        npHour = findViewById(R.id.np_hour_schedule);
        npMinute = findViewById(R.id.np_minute_schedule);
        npAmPm = findViewById(R.id.np_am_pm_schedule);
        npServings = findViewById(R.id.np_servings_schedule);
        numberPickerConfig();
    }

    private void numberPickerConfig() {
        npHour.setMinValue(1);
        npHour.setMaxValue(12);

        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);

        npAmPm.setMinValue(0);
        npAmPm.setMaxValue(1);
        npAmPm.setDisplayedValues(new String[]{"AM", "PM"});

        npHour.setFormatter(value -> String.format(Locale.US, "%02d", value));
        npMinute.setFormatter(value -> String.format(Locale.US, "%02d", value));

        npServings.setMinValue(Feed.MIN_FEED_AMOUNT);
        npServings.setMaxValue(Feed.MAX_FEED_AMOUNT);
    }
}