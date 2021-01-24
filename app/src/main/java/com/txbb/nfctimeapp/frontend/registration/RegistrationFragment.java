package com.txbb.nfctimeapp.frontend.registration;

import android.app.AppOpsManager;
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
import com.txbb.nfctimeapp.frontend.AppState;

import java.util.Map;

public class RegistrationFragment extends Fragment implements Actor {

    private RegistrationViewModel mViewModel;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((CustomActivity) getActivity()).setState(AppState.REGISTRATION);

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

        /* Assume success */

        this.displayMessage("Success! " + tagId);

    }


    @Override
    public void onTagRegisterSuccess(String id) {
        this.setTagInfo(id);

        ((CustomActivity) getActivity()).setState(AppState.STANDARD);

        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_registrationFragment_to_nav_home);
    }

    @Override
    public void onBadRegister() {
        this.displayMessage("This tag is already in use!");
    }

    @Override
    public void onTagRegisterFailure() {
        this.displayMessage("Tag linking failed. Please scan again.");

        ((CustomActivity) getActivity()).setState(AppState.STANDARD);

        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.action_registrationFragment_to_designerFragment);
    }

    @Override
    public void onScanRequest(AppState appState) {
        ((CustomActivity) getActivity()).setState(appState);
        this.displayMessage("Please scan the tag again to finish linking.");
    }

    @Override
    public void sync(Map<String, TagProperties> tags) {

    }

    private void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}