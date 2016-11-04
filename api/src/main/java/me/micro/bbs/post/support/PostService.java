package me.micro.bbs.post.support;

import me.micro.bbs.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * PostService
 *
 * for Cache ...
 *
 * Created by microacup on 2016/11/2.
 */
@Service
public class PostService {
    public static final String CACHES_NAME = "cache.posts";
    public static final String CACHE_NAME = "cache.post";
    public static final Class<?> CACHE_TYPE = Post.class;

    @Autowired
    private PostRepository postRepository;

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findByTags(Collection<Long> tags) {
        List<Post> posts = postRepository.findByTags(tags);
        return posts;
    }

    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findByCategoryId(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts;
    }


}
