package me.micro.bbs.post.support;

import me.micro.bbs.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    //@Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public Page<Post> findAll(int page, int pageSize) {
        return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastReplyTime", "updatedTime"));
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    //@Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public Page<Post> findByTags(Collection<Long> tags, int page, int pageSize) {
        Page<Post> posts = postRepository.findByTags(tags,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastReplyTime", "updatedTime"));
        return posts;
    }

    //@Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public Page<Post> findByCategoryId(Long categoryId, int page, int pageSize) {
        Page<Post> posts = postRepository.findByCategoryId(categoryId,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastReplyTime", "updatedTime"));
        return posts;
    }


    /**
     * 热门
     */
    public Page<Post> hot(int page, int pageSize) {
        return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC, "topTime", "replyCount", "lastReplyTime", "updatedTime"));
    }

    /**
     * 优选
     */
    public Page<Post> perfect(int page, int pageSize) {
        return postRepository.findByPerfectTrueOrderByPerfectTimeDesc(new PageRequest(page, pageSize));
    }

    /**
     * 此刻
     */
    public Page<Post> findNow(int page, int pageSize) {
       return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC, "lastReplyTime","updatedTime"));
    }

    // 查询前5名
    public List<Post> findTop5Now() {
        return postRepository.findTop5ByOrderByLastReplyTimeDesc();
    }

}
