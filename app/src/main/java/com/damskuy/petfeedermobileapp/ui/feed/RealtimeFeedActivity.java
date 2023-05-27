package com.damskuy.petfeedermobileapp.ui.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.api.retrofit.RetrofitClient;
import com.damskuy.petfeedermobileapp.data.model.RealtimeFeedRequest;
import com.damskuy.petfeedermobileapp.data.model.RealtimeFeedResponse;
import com.damskuy.petfeedermobileapp.api.retrofit.RetrofitService;
import com.damskuy.petfeedermobileapp.data.model.Feed;
import com.damskuy.petfeedermobileapp.helpers.ViewHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealtimeFeedActivity extends AppCompatActivity {

    private NumberPicker npServings;
    private Button btnFeed, btnCancel;
    private RetrofitService apiService;
    private SweetAlertDialog loadingDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_feed);
        context = RealtimeFeedActivity.this;
        initUI();
        initHandlers();
    }

    private void initUI() {
       npServings = findViewById(R.id.np_servings_realtime);
       btnFeed = findViewById(R.id.btn_feed_realtime);
       btnCancel = findViewById(R.id.btn_cancel_realtime);
       numberPickerConfig();
    }

    private void numberPickerConfig() {
        npServings.setMinValue(Feed.MIN_FEED_AMOUNT);
        npServings.setMaxValue(Feed.MAX_FEED_AMOUNT);
    }

    private void initHandlers() {
        btnFeed.setOnClickListener(v -> {
            int feedAmount = npServings.getValue();
            apiService = RetrofitClient.getClient().create(RetrofitService.class);

            RealtimeFeedRequest requestData = new RealtimeFeedRequest("592f5ec4-c3dd-4d5c-93fe-4ee3db513ad7", feedAmount);
            Call<RealtimeFeedResponse> call = apiService.realtimeFeed(requestData);
            loadingDialog = ViewHelper.showLoadingDialog(this);

            call.enqueue(new Callback<RealtimeFeedResponse>() {
                @Override
                public void onResponse(@NonNull Call<RealtimeFeedResponse> call, @NonNull Response<RealtimeFeedResponse> response) {
                    ViewHelper.hideLoadingDialog(loadingDialog);
                    if (response.isSuccessful()) {
                        RealtimeFeedResponse responseData = response.body();
                        if (responseData != null) {
                            RealtimeFeedResponse.Data data  = responseData.getData();
                            ViewHelper.fireSuccessAlert(context, "Fed at : " + data.getCreatedAt());
                        }
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            try {
                                String errorBodyString = errorBody.string();
                                JsonObject jsonObject = new Gson().fromJson(errorBodyString, JsonObject.class);
                                if (jsonObject.has("error")) {
                                    String errorMessage = jsonObject.get("error").getAsString();
                                    ViewHelper.fireErrorAlert(context, errorMessage);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<RealtimeFeedResponse> call, @NonNull Throwable t) {
                    ViewHelper.hideLoadingDialog(loadingDialog);
                    ViewHelper.fireErrorAlert(context, "Connection error");
                }
            });
        });

        btnCancel.setOnClickListener(v -> onBackPressed());
    }
}