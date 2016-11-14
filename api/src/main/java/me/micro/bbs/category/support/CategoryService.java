package me.micro.bbs.category.support;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CategoryService
 *
 * Created by microacup on 2016/11/3.
 */
@Service
public class CategoryService {
    public static final String CACHES_NAME = "cache.cates";
    public static final String CACHE_NAME = "cache.cate";
    public static final Class<?> CACHE_TYPE = Category.class;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryFormAdpater formAdpater;

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Category findOne(Long id) {
        return categoryRepository.findOne(id);
    }

    /**
     * 添加
     *
     * @param categoryForm
     * @return
     */
    @CacheEvict(value = CACHES_NAME, allEntries = true)
    public Category addCategory(CategoryForm categoryForm) {
        Category category = formAdpater.createCategory(categoryForm);
        Category saved = categoryRepository.save(category);
        //saveToIndex(saved);
        return saved;
    }

    @CacheEvict(value = CACHES_NAME, allEntries = true)
    public Category updateCategory(Category category, CategoryForm categoryForm) {
        formAdpater.updateCategory(category, categoryForm);
        Category saved = categoryRepository.save(category);
        return saved;
    }

    @CacheEvict(value = CACHES_NAME, allEntries = true)
    public void deleteCategory(long id) {
        categoryRepository.delete(id);
    }

}
