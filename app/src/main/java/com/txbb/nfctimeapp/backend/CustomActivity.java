package com.txbb.nfctimeapp.backend;

import android.app.FragmentManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.txbb.nfctimeapp.FrontBackInterface;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryManager;
import com.txbb.nfctimeapp.frontend.home.HomeFragment;
import com.txbb.nfctimeapp.frontend.registration.DesignerFragment;
import com.txbb.nfctimeapp.frontend.registration.RegistrationFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    // interfacing
    protected FrontBackInterface frontBackInterface;

    // registration variables
    protected String chosenName;
    protected int chosenCategory;

    // other variables
    protected String selectedTagId;
    protected String selectedTagTitle;
    protected Category selectedTagCategory;

    protected CategoryManager categoryManager;

    protected List<Actor> getFragments() {
        List<Actor> actors = new ArrayList<>();

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        actors.add((Actor) fragment);

//        for (Fragment fragment : this.getSupportFragmentManager().getFragments()) {
//            System.out.println(fragment);
//            if (fragment instanceof HomeFragment) {
//                actors.add((Actor) fragment);
//            } else {
//                //throw new RuntimeException("Invalid fragment found.");
//            }
//        }

        return actors;
    }

    public void initCategoryManager() {
        this.categoryManager = new CategoryManager();
    }

    public CategoryManager getCategoryManager() {
        return this.categoryManager;
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

    public String getSelectedTagId() {
        return this.selectedTagId;
    }

    public void setSelectedTagId(String selectedTagId) {
        this.selectedTagId = selectedTagId;
    }

    public String getSelectedTagTitle() {
        return this.selectedTagTitle;
    }

    public void setSelectedTagTitle(String selectedTagTitle) {
        this.selectedTagTitle = selectedTagTitle;
    }

    public Category getSelectedTagCategory() {
        return this.selectedTagCategory;
    }

    public void setSelectedTagCategory(Category selectedTagCategory) {
        this.selectedTagCategory = selectedTagCategory;
    }
}
