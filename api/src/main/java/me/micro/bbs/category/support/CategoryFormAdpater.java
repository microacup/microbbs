package me.micro.bbs.category.support;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.CategoryForm;
import org.springframework.stereotype.Service;

/**
 * CategoryFormAdpater
 *
 * Created by microacup on 2016/11/14.
 */
@Service
class CategoryFormAdpater {

    public Category createCategory(CategoryForm categoryForm) {
        Category category = new Category();
        category.setCode(categoryForm.getCode());
        category.setTitle(categoryForm.getTitle());

        return category;
    }

    public void updateCategory(Category category, CategoryForm categoryForm) {
        category.setCode(categoryForm.getCode());
        category.setTitle(categoryForm.getTitle());
    }


}
