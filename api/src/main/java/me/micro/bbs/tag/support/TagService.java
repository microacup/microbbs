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

    /**
     * 根据分类找标签
     * @param categoryId
     * @return
     */
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Tag> findByCategory(Long categoryId) {
        return tagRepository.findByCategoryId(categoryId);
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Tag findOne(Long id) {
        return tagRepository.findOne(id);
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

}
