package com.txbb.nfctimeapp.frontend;

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
import com.txbb.nfctimeapp.TagProperties;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.category.CategoryImageButton;
import com.txbb.nfctimeapp.category.CategoryManager;
import com.txbb.nfctimeapp.frontend.registration.DesignerFragment;
import com.txbb.nfctimeapp.frontend.registration.DesignerViewModel;
import com.txbb.nfctimeapp.util.Units;

import java.util.List;
import java.util.Map;
import java.util.Random;


public class GeneralEditorFragment extends Fragment {
    protected CategoryButtonGroup buttonGroup;

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
        this.buttonGroup = new CategoryButtonGroup();
    }


    protected TableRow newRow(int tableLayoutId) {
        TableRow tableRow = new TableRow(getActivity());

        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);


        tableRow.setLayoutParams(layoutParams);
        TableLayout tableLayout = getActivity().findViewById(tableLayoutId);
        tableLayout.addView(tableRow);


        return tableRow;
    }


    /**
     * Adds category buttons into the TableLayout view in this activity.
     */
    protected void addCategoryButtons(int tableLayoutId) {

        TableRow iconRow = newRow(tableLayoutId);
        TableRow captionRow = newRow(tableLayoutId);

        CategoryManager categoryManager = ((CustomActivity) getActivity()).getCategoryManager();
        List<Category> categoryList = categoryManager.getCategories();

        for (int i = 0; i < categoryList.size(); i ++) {

            // add button to table
            Category category = categoryList.get(i);
            CategoryImageButton button = new CategoryImageButton(getActivity(), category.getIcon(),
                    new Random().nextInt(16777216), category, this);    // set random colour
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
                iconRow = newRow(tableLayoutId);
                captionRow = newRow(tableLayoutId);
            }
        }
    }


    protected boolean hasSelectedCategory() {
        return this.buttonGroup.isSelected();
    }

}