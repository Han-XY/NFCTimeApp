package com.txbb.nfctimeapp.frontend.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.txbb.nfctimeapp.FrontBackInterface;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.category.CategoryManager;
import com.txbb.nfctimeapp.frontend.AddTagActivity;
import com.txbb.nfctimeapp.frontend.AppState;
import com.txbb.nfctimeapp.frontend.MainActivity;
import com.txbb.nfctimeapp.frontend.registration.RegistrationFragment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements Actor {

    private HomeViewModel homeViewModel;
    private List<TagCardView> tagCardViews;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    private void setUp() {
        FrontBackInterface frontBackInterface = ((CustomActivity) getActivity()).getFrontBackInterface();

        /* Start a thread to update info periodically */

        this.tagCardViews = new ArrayList<>();

        SearchView searchView = getActivity().findViewById(R.id.homeSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("NFC", "Text change detected.");
                filterTags(newText);
                return true;
            }
        });

        if (!((CustomActivity) getActivity()).isFetchThreadRunning()) {
            ((CustomActivity) getActivity()).setFetchThreadRunning(true);
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);

                            if (getActivity() == null) {
                                continue;
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("Sync loop", "Syncing");
                                    frontBackInterface.syncRequest();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setUp();

        FloatingActionButton fab = getActivity().findViewById(R.id.home_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment =
                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_nav_home_to_designerFragment);

            }
        });
    }

    private void filterTags(String newText) {
        LinearLayout linearLayout = getActivity().findViewById(R.id.lin_layout);

        for (TagCardView tagCardView : this.tagCardViews) {
            linearLayout.removeView(tagCardView);
        }

        for (TagCardView tagCardView : this.tagCardViews) {
            if (tagCardView.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                linearLayout.addView(tagCardView);
            }
        }
    }

    private void addNewCard(String tagId, String tagName, Category category, long duration, long totalDuration, boolean active) {
        String categoryName = category.getName();
        int categoryIconId = category.getIcon();

        for (TagCardView tagCardView : this.tagCardViews) {
            if (tagCardView.getTagId().equals(tagId)) {
                tagCardView.updateText(duration, totalDuration, active);
                return;
            }
        }

        TagCardView tagCardView = new TagCardView(getContext(), this, tagId, tagName, category);
        tagCardView.updateText(duration, totalDuration, active);
        this.tagCardViews.add(tagCardView);

        LinearLayout linearLayout = getActivity().findViewById(R.id.lin_layout);
        linearLayout.addView(tagCardView);
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
        for (String tagId : tags.keySet()) {
            Log.i("NFC", "Sync initiated");

            TagProperties tagProperties = tags.get(tagId);
            long startTime = tagProperties.getStartTime();

            Log.i("TXBB1003", tagId);
            Log.i("TXBB1003", Long.toString(startTime));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long currentTime = timestamp.getTime() / 1000;
            long duration = currentTime - startTime;

            int categoryId = tagProperties.getCategory();
            CategoryManager categoryManager = ((CustomActivity) getActivity()).getCategoryManager();
            Category category = categoryManager.getCategoryFromId(categoryId);

            long totalDuration = tagProperties.getDurationToday();
            boolean active = tagProperties.getStartTime() != -1;

            String tagName = tagProperties.getName();

            this.addNewCard(tagId, tagName, category, duration, totalDuration, active);
        }
    }

    @Override
    public void onTagStart(String id, long startTime, long durationToday) {
        Log.i("TXBB1001", "HomeFragment::onTagStart");

        for (TagCardView tagCardView : this.tagCardViews) {
            if (tagCardView.getTagId().equals(id)) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                long currentTime = timestamp.getTime() / 1000;
                long duration = currentTime - startTime;

                tagCardView.updateText(duration, durationToday, true);
            }
        }

    }

    @Override
    public void onTagStop(String id, long startTime, long durationToday) {
        Log.i("TXBB1001", "HomeFragment::onTagStop");

        Log.i("TXBB1001", id);

        for (TagCardView tagCardView : this.tagCardViews) {
            if (tagCardView.getTagId().equals(id)) {
                Log.i("TXBB1001", "1123123");
                tagCardView.updateText(0, durationToday, false);
            }
        }
    }
}