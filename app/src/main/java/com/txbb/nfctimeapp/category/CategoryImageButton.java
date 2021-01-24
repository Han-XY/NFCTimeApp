package com.txbb.nfctimeapp.category;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.fragment.app.Fragment;

import com.txbb.nfctimeapp.R;
import com.txbb.nfctimeapp.backend.CustomActivity;
import com.txbb.nfctimeapp.util.Units;

public class CategoryImageButton extends androidx.appcompat.widget.AppCompatImageButton implements View.OnClickListener {

    private CategoryButtonGroup group;
    private int selectionColor;
    private Category category;
    private Fragment parentFragment;

    public CategoryImageButton(Context context, int image, int selectionColor, Category category, Fragment parentFragment) {
        super(context);
        this.setImageResource(image);
        this.setBackgroundResource(R.drawable.ic_btn);
        this.selectionColor = selectionColor;
        this.category = category;
        this.parentFragment = parentFragment;
        init();
    }

    public CategoryImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(this);
        this.setScaleType(ScaleType.CENTER_INSIDE);
        this.setForegroundGravity(Gravity.CENTER_HORIZONTAL);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int sideLength = Units.dpConvert(50, displayMetrics);
        int padding = Units.dpConvert(8, displayMetrics);
        int marginX = Units.dpConvert(21 , displayMetrics);
        int marginY = Units.dpConvert(5, displayMetrics);

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(sideLength, sideLength);
        layoutParams.setMargins(marginX, 0, marginX, marginY);

        this.setPadding(padding, padding, padding, padding);
        this.setLayoutParams(layoutParams);
    }

    /**
     * Regsisters the category button with a button group for managing radio button effects.
     * @param group The group for the button.
     */
    public void registerGroup(CategoryButtonGroup group) {
        this.group = group;
    }

    @Override
    public void onClick(View v) {
        this.group.onButtonClick(this);
    }

    /**
     * Toggles the visual effect of this button to be 'set'. Should only be called within its own
     * package.
     * @see com.txbb.nfctimeapp.category
     */
    void toggleSetEffect() {
        this.setBackgroundResource(R.drawable.ic_btn2);
        this.getBackground().setColorFilter(new LightingColorFilter(0x000000, this.selectionColor));
        this.setColorFilter(Color.argb(255, 255, 255, 255));
    }

    /**
     * Toggles the visual effect of this button to be 'unset'. Should only be called within its own
     * package.
     * @see com.txbb.nfctimeapp.category
     */
    void toggleUnsetEffect() {
        this.setBackgroundResource(R.drawable.ic_btn);
        //this.getBackground().setColorFilter(null);
        this.setColorFilter(Color.argb(255, 0, 0, 0));
    }

    public Category getCategory() {
        return this.category;
    }

}
