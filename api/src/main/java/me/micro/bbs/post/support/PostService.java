package me.micro.bbs.post.support;

import me.micro.bbs.post.Post;
import me.micro.bbs.post.PostForm;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
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
    public static final String CACHES_REALTIME_NAME = "cache.posts.realtime";
    public static final String CACHE_NAME = "cache.post";
    public static final Class<?> CACHE_TYPE = Post.class;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostFormAdapter postFormAdapter;

    public Page<Post> findAll(int page, int pageSize) {
        return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "cacheKeyGenerator")
    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    public Page<Post> findByTags(Collection<Long> tags, int page, int pageSize) {
        Page<Post> posts = postRepository.findByTags(tags,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
        return posts;
    }

    public Page<Post> findByCategoryId(Long categoryId, int page, int pageSize) {
        Page<Post> posts = postRepository.findByCategoryId(categoryId,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
        return posts;
    }


    /**
     * 热门
     */
    public Page<Post> hot(int page, int pageSize) {
        return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC, "topTime", "replyCount", "lastTime"));
    }

    /**
     * 优选
     */
    public Page<Post> perfect(int page, int pageSize) {
        return postRepository.findByPerfectTrue(new PageRequest(page, pageSize, Sort.Direction.DESC, "perfectTime", "lastTime"));
    }

    /**
     * 此刻
     */
    public Page<Post> findNow(int page, int pageSize) {
       return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC, "lastTime"));
    }

    // 查询前5名
    @Cacheable(value = CACHES_REALTIME_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findTop5Now() {
        return postRepository.findTop5ByOrderByLastReplyTimeDesc();
    }

    // 相关话题
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findTop5RelatedPosts(Long postId) {
        List<Tag> tags = tagService.findByPostId(postId);
        List<Post> posts = postRepository.findTop5DistinctByIdNotAndTagsInOrderByUpdatedTimeDesc(postId, tags);
        return posts;
    }


    // 添加Post
    public Post addPost(PostForm postForm, String username) {
        Post post = postFormAdapter.createPostFromPostForm(postForm, username);
        Post saved = postRepository.save(post);
        // saveToIndex(post);
        return saved;
    }


}
