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

        /*
            TODO: For now we are just creating 50 test categories at runtime.
            Ideally these should be read from disc.
        */

        for (int i = 0; i < 17; i ++) {
            int iconId = new int[]{R.drawable.ic_book, R.drawable.ic_gym,
                    R.drawable.ic_music,  R.drawable.ic_pancake,
            }[new Random().nextInt(4)];
        }
    }

    private void fetchCategories() {
        categoryList.add(new Category("Reading", R.drawable.ic_book));
        categoryList.add(new Category("Exercise", R.drawable.ic_gym));
        categoryList.add(new Category("Cooking", R.drawable.ic_pancake));
        categoryList.add(new Category("Guitar", R.drawable.ic_music));
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

        this.categoryList.add(new Category(name, icon));

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
