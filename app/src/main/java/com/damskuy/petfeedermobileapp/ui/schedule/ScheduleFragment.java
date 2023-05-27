package com.damskuy.petfeedermobileapp.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.ui.feed.ScheduledFeedActivity;

public class ScheduleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View scheduleView = inflater.inflate(R.layout.schedule_layout, container, false);
        Button btn = scheduleView.findViewById(R.id.add_schedule_btn);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScheduledFeedActivity.class);
            startActivity(intent);
        });
        return scheduleView;
    }
}
