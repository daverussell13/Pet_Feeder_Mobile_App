package com.damskuy.petfeedermobileapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.damskuy.petfeedermobileapp.R;
import com.damskuy.petfeedermobileapp.data.auth.AuthRepository;

public class HomeFragment extends Fragment {

    private TextView txtUserGreet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        txtUserGreet = view.findViewById(R.id.txt_user_greet_home);
        txtUserGreet.setText(getString(R.string.home_user_greet, AuthRepository.getInstance().getAuthenticatedUser().getName()));
        return view;
    }
}
