package me.micro.bbs.tag.support;

import com.google.common.collect.Lists;
import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.exception.MicroException;
import me.micro.bbs.post.support.PostRepository;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.TagForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TagService
 *
 * Created by microacup on 2016/11/3.
 */
@Service
@Transactional
public class TagService {
    public static final String CACHES_NAME = "cache.tags";
    public static final String CACHE_NAME = "cache.tag";
    public static final Class<?> CACHE_TYPE = Tag.class;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostRepository postRepository;

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    /**
     * 根据分类找标签
     * @param categoryCode
     * @return
     */
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findByCategory(String categoryCode) {
        return tagRepository.findByCategory(categoryCode);
    }

    // 是否存在此分类下的标签
    public Long countByCategoryId(Long categoryId) {
        return tagRepository.countByCategoryId(categoryId);
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Tag findOne(Long id) {
        return tagRepository.findOne(id);
    }

    @Cacheable(value = CACHE_NAME, key = "#code")
    public Tag findByCode(String code) {
        return tagRepository.findByCode(code);
    }

    /**
     * 热门标签
     *
     * @param n 数量
     * @return
     */
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findTopN(int n) {
        return tagRepository.findTopN(n);
    }


    // 查找话题对应的标签
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findByPostId(long postId) {
        return tagRepository.findByPostId(postId);
    }

    public Map<Category, List<Tag>> getTagsMap() {
        Map<Category, List<Tag>> tags = new HashMap<>();
        List<Tag> list = this.findAll();
        for (Tag tag : list) {
            Category category = tag.getCategory();
            List<Tag> ts = tags.get(category);
            if (ts == null) {
                ts = Lists.newArrayList();
                tags.put(category, ts);
            }
            ts.add(tag);
        }
        return tags;
    }


    @CacheEvict(value = CACHES_NAME, allEntries = true)
    public Tag addTag(TagForm tagForm) {
        Category category = categoryService.findOne(tagForm.getCategoryId());
        if (category == null) {
            return null;
        }

        Tag tag = new Tag();
        tag.setCode(tagForm.getCode());
        tag.setTitle(tagForm.getTitle());
        tag.setCategory(category);
        tagRepository.save(tag);
        return tag;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHES_NAME, allEntries = true),
            @CacheEvict(value = CACHE_NAME, key = "#one.id")
    })
    public Tag update(Tag one, TagForm tagForm) {
        Category category = categoryService.findOne(tagForm.getCategoryId());
        if (category == null) {
            return null;
        }

        one.setCode(tagForm.getCode());
        one.setTitle(tagForm.getTitle());
        one.setCategory(category);
        tagRepository.save(one);
        return one;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHES_NAME, allEntries = true),
            @CacheEvict(value = CACHE_NAME, key = "#id")
    })
    public void delete(Long id) throws MicroException {
        Long count = postRepository.countByTagId(id);
        if (count > 0) {
            throw new MicroException("删除失败，该标签下还有" + count + "个话题");
        }
        tagRepository.delete(id);
    }

}
