package com.txbb.nfctimeapp.frontend.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.category.CategoryImageButton;
import com.txbb.nfctimeapp.category.CategoryManager;
import com.txbb.nfctimeapp.util.Units;

import java.util.List;
import java.util.Random;

public class DesignerFragment extends Fragment {

    private CategoryManager categoryManager;

    private CategoryButtonGroup buttonGroup;

    private DesignerViewModel mViewModel;

    public static DesignerFragment newInstance() {
        return new DesignerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.designer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(DesignerViewModel.class);
        // TODO: Use the ViewModel

        CustomActivity activity = (CustomActivity) getActivity();

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

        this.categoryManager = new CategoryManager();
        this.buttonGroup = new CategoryButtonGroup();
        this.addCategoryButtons();
    }


    private TableRow newRow() {
        TableRow tableRow = new TableRow(getActivity());

        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);


        tableRow.setLayoutParams(layoutParams);
        TableLayout tableLayout = getActivity().findViewById(R.id.table_layout);
        tableLayout.addView(tableRow);

        return tableRow;
    }


    /**
     * Adds category buttons into the TableLayout view in this activity.
     */
    private void addCategoryButtons() {

        TableRow iconRow = newRow();
        TableRow captionRow = newRow();

        List<Category> categoryList = this.categoryManager.getCategories();

        for (int i = 0; i < categoryList.size(); i ++) {

            // add button to table
            Category category = categoryList.get(i);
            CategoryImageButton button = new CategoryImageButton(getActivity(), category.getIcon(),
                    new Random().nextInt(16777216), category);    // set random colour
            this.buttonGroup.registerButton(button);
            iconRow.addView(button);

            // add label underneath button
            TextView textView = new TextView(getActivity());
            textView.setText(category.getName());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);

            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
            );

            layoutParams.setMargins(0, 0, 0, Units.dpConvert(28, displayMetrics));

            textView.setLayoutParams(layoutParams);
            captionRow.addView(textView);

            if ((i + 1) % 4 == 0) {
                iconRow = newRow();
                captionRow = newRow();
            }
        }
    }

    private boolean hasSelectedCategory() {
        return this.buttonGroup.isSelected();
    }
}