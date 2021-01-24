package com.txbb.nfctimeapp.frontend.settings;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.frontend.AppState;

import java.util.Map;

public class SettingsFragment extends Fragment implements Actor {

    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onTagRegisterSuccess(String id) {

    }

    @Override
    public void onTagRegisterFailure() {

    }

    @Override
    public void onScanRequest(AppState nextState) {

    }

    @Override
    public void onBadRegister() {

    }

    @Override
    public void sync(Map<String, TagProperties> tags) {

    }

    @Override
    public void onTagStart(String id, long startTime, long durationToday) {

    }

    @Override
    public void onTagStop(String id, long startTime, long durationToday) {

    }
}