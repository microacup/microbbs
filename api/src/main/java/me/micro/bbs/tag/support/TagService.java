package me.micro.bbs.tag.support;

import me.micro.bbs.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TagService
 *
 * Created by microacup on 2016/11/3.
 */
@Service
public class TagService {
    public static final String CACHES_NAME = "cache.tags";
    public static final String CACHE_NAME = "cache.tag";
    public static final Class<?> CACHE_TYPE = Tag.class;

    @Autowired
    private TagRepository tagRepository;

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findByCategory(Long categoryId) {
        return tagRepository.findByCategoryId(categoryId);
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Tag findOne(Long id) {
        return tagRepository.findOne(id);
    }

}
