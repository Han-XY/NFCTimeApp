package com.txbb.nfctimeapp.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.txbb.nfctimeapp.R;


public class CategoryManager {

    private List<Category> categoryList;

    public CategoryManager() {
        this.categoryList = new ArrayList<>();

        this.fetchCategories();
    }

    private void fetchCategories() {
        categoryList.add(new Category("Reading", R.drawable.ic_book, 1));
        categoryList.add(new Category("Exercise", R.drawable.ic_gym, 2));
        categoryList.add(new Category("Cooking", R.drawable.ic_pancake, 3));
        categoryList.add(new Category("Guitar", R.drawable.ic_music, 4));
    }

    /**
     * Creates and adds a new category.
     * @param name The name of the category.
     * @param icon The icon id to use.
     * @return <code>true</code> if a category is successfully created and added. Returns
     * <code>false</code> if there is an existing category with the same name.
     */
    public boolean createCategory(String name, int icon) {
        if (this.hasCategory(name)) {
            return false;
        }

        this.categoryList.add(new Category(name, icon, 5));

        return true;
    }

    private boolean hasCategory(String name) {
        for (Category category : this.categoryList) {
            if (category.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private void deleteCategory(String name) {
        for (Iterator<Category> it = this.categoryList.iterator(); it.hasNext(); ) {
            Category category = it.next();
            if (category.getName().equals(name)) {
                it.remove();
                return;
            }
        }
    }

    public List<Category> getCategories() {
        return this.categoryList;
    }

}
