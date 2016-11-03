package me.micro.bbs.category.support;

import me.micro.bbs.category.Category;
import me.micro.bbs.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static final String CACHE_NAME = "cache.cates";
    public static final Class<?> CACHE_TYPE = Tag.class;

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long id) {
        return categoryRepository.findOne(id);
    }

}
