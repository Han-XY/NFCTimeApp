package com.txbb.nfctimeapp.backend;

import android.app.FragmentManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.txbb.nfctimeapp.FrontBackInterface;
import com.txbb.nfctimeapp.frontend.registration.RegistrationFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    // interfacing
    protected FrontBackInterface frontBackInterface;

    // registration variables
    protected String chosenName;
    protected int chosenCategory;

    protected List<Actor> getFragments() {
        List<Actor> actors = new ArrayList<>();

        for (Fragment fragment : this.getSupportFragmentManager().getFragments()) {
            if (fragment instanceof Actor) {
                actors.add((Actor) fragment);
            } else {
                throw new RuntimeException("Invalid fragment found.");
            }
        }

        return actors;
    }

    public void setChosenName(String chosenName) {
        this.chosenName = chosenName;
    }

    public void setChosenCategory(int chosenCategory) {
        this.chosenCategory = chosenCategory;
    }

    public String getChosenName() {
        return this.chosenName;
    }

    public int getChosenCategory() {
        return this.chosenCategory;
    }

    public FrontBackInterface getFrontBackInterface() {
        return this.frontBackInterface;
    }
}
