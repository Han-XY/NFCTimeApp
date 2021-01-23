package com.txbb.nfctimeapp.category;

import java.util.ArrayList;

public class CategoryButtonGroup {

    private ArrayList<CategoryImageButton> buttons;
    private CategoryImageButton selected;

    public CategoryButtonGroup() {
        this.buttons = new ArrayList<CategoryImageButton>();
    }

    /**
     * Registers a <code>CustomImageButton</code> to be managed.
     * @param button
     */
    public void registerButton(CategoryImageButton button) {
        this.buttons.add(button);
        button.registerGroup(this);
    }

    /**
     * Event listener called when a <code>CustomImageButton</code> belonging to this group is
     * clicked.
     * @param button The instance of the button that is clicked.
     */
    void onButtonClick(CategoryImageButton button) {
        if (selected == null) {
            selected = button;
            button.toggleSetEffect();
            return;
        }

        selected.toggleUnsetEffect();

        if (selected == button) {
            selected = null;
        } else {
            selected = button;
            selected.toggleSetEffect();
        }
    }

    public boolean isSelected() {
        return this.selected != null;
    }

    public int getSelectedCategory() {
        return this.selected.getCategory().getCategoryId();
    }

    public void select(int categoryId) {
        if (this.selected != null) {
            selected.toggleUnsetEffect();
        }

        for (CategoryImageButton categoryImageButton : this.buttons) {
            if (categoryImageButton.getCategory().getCategoryId() == categoryId) {
                categoryImageButton.toggleSetEffect();
                selected = categoryImageButton;
                return;
            }
        }
     }
}
