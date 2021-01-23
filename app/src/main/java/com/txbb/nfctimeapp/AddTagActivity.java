package com.txbb.nfctimeapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.txbb.nfctimeapp.category.Category;
import com.txbb.nfctimeapp.category.CategoryButtonGroup;
import com.txbb.nfctimeapp.category.CategoryImageButton;
import com.txbb.nfctimeapp.category.CategoryManager;
import com.txbb.nfctimeapp.util.Units;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class AddTagActivity extends AppCompatActivity {

    //TODO: Change this to be global
    private CategoryManager categoryManager;

    private CategoryButtonGroup buttonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New activity!");  // provide compatibility to all the versions

        this.categoryManager = new CategoryManager();
        this.buttonGroup = new CategoryButtonGroup();
        this.addCategoryButtons();
    }

    private TableRow newRow() {
        TableRow tableRow = new TableRow(this);

        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);


        tableRow.setLayoutParams(layoutParams);
        TableLayout tableLayout = findViewById(R.id.table_layout);
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
            CategoryImageButton button = new CategoryImageButton(this, category.getIcon(),
                    new Random().nextInt(16777216));    // set random colour
            this.buttonGroup.registerButton(button);
            iconRow.addView(button);

            // add label underneath button
            TextView textView = new TextView(this);
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

}