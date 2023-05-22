package com.damskuy.petfeedermobileapp.ui.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.api.retrofit.RetrofitClient;
import com.damskuy.petfeedermobileapp.api.retrofit.model.RealtimeRequest;
import com.damskuy.petfeedermobileapp.api.retrofit.model.RealtimeResponse;
import com.damskuy.petfeedermobileapp.api.retrofit.service.FeedService;
import com.damskuy.petfeedermobileapp.data.model.Feed;
import com.damskuy.petfeedermobileapp.helpers.ViewHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class RealtimeFeedActivity extends AppCompatActivity {

    private NumberPicker npServings;
    private Button btnFeed, btnCancel;
    private FeedService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_feed);
        initUI();
        initHandlers();
    }

    private void initUI() {
       npServings = findViewById(R.id.np_servings_realtime);
       npServings.setMinValue(Feed.MIN_SERVING);
       npServings.setMaxValue(Feed.MAX_SERVING);
       btnFeed = findViewById(R.id.btn_feed_realtime);
       btnCancel = findViewById(R.id.btn_cancel_realtime);
    }

    private void initHandlers() {
        btnFeed.setOnClickListener(v -> {
            ViewHelper.fireErrorAlert(this, "Realtime feed failed");
            apiService = RetrofitClient.getClient().create(FeedService.class);

            RealtimeRequest requestData = new RealtimeRequest("592f5ec4-c3dd-4d5c-93fe-4ee3db513ad7", 2);
            Call<RealtimeResponse> call = apiService.realtimeFeed(requestData);
        });
        btnCancel.setOnClickListener(v -> onBackPressed());
    }
}