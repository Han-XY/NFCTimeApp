package com.txbb.nfctimeapp.frontend.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.CustomActivity;

import java.util.Map;

public class RegistrationFragment extends Fragment implements Actor {

    private RegistrationViewModel mViewModel;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setTagInfo(String tagId) {
        String chosenName = ((CustomActivity) getActivity()).getChosenName();
        int chosenCategory = ((CustomActivity) getActivity()).getChosenCategory();

        TagProperties tagProperties = new TagProperties(chosenName, chosenCategory);

        ((CustomActivity) getActivity()).getFrontBackInterface().
                updateTagProperties(tagId, tagProperties);

        this.displayMessage("Success!");

        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_registrationFragment_to_nav_home);
    }

    @Override
    public void onUnknownTagRead() {
        this.displayMessage("Please scan again to register.");
    }

    @Override
    public void onKnownTagRead() {
        this.displayMessage("This tag is already used. Please unlink it or try another.");
    }

    @Override
    public void onTagRegister(String id) {
        this.setTagInfo(id);
    }

    @Override
    public void sync(Map<String, TagProperties> tags) {

    }

    private void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}