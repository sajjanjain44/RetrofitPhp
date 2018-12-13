package com.zxsofficials.retrofitphp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxsofficials.retrofitphp.R;
import com.zxsofficials.retrofitphp.storage.SharedPrefManager;

public class HomeFragment extends Fragment {
    private TextView textviewEmail, textViewName,textViewSchool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textviewEmail = view.findViewById(R.id.textviewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewSchool = view.findViewById(R.id.textViewSchool);

        textviewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
        textViewSchool.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());

    }
}
