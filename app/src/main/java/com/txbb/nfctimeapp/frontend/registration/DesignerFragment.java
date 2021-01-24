package com.txbb.nfctimeapp.frontend.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.Actor;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.frontend.AppState;
import com.txbb.nfctimeapp.frontend.GeneralEditorFragment;

import java.util.Map;

public class DesignerFragment extends GeneralEditorFragment implements Actor {

    private DesignerViewModel mViewModel;

    public static DesignerFragment newInstance() {
        return new DesignerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Design your activity");

        return inflater.inflate(R.layout.designer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomActivity activity = (CustomActivity) getActivity();
        CategoryButtonGroup buttonGroup = this.buttonGroup;

        FloatingActionButton fab = getActivity().findViewById(R.id.designer_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getActivity().findViewById(R.id.editText);
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!hasSelectedCategory()) {
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_LONG).show();
                    return;
                }

                activity.setChosenName(editText.getText().toString());
                activity.setChosenCategory(buttonGroup.getSelectedCategory());

                NavHostFragment navHostFragment =
                        (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_designerFragment_to_registrationFragment);
            }
        });

        this.addCategoryButtons(R.id.designerTableLayout);
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