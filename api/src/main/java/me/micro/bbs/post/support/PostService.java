package me.micro.bbs.post.support;

import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.PostForm;
import me.micro.bbs.reply.support.ReplyRepository;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
import me.micro.bbs.user.support.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ReplyRepository replyRepository;

    public Page<Post> findAll(int page, int pageSize) {
        return postRepository.findAll(new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
    }

    public Page<Post> findActived(int page, int pageSize) {
        return postRepository.findByStatus(PostStatus.actived, new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Post findOne(Long id) {
        return postRepository.findByIdAndStatus(id, PostStatus.actived);
    }

    public Page<Post> findByTags(Collection<String> tags, int page, int pageSize) {
        Page<Post> posts = postRepository.findByTags(tags,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
        return posts;
    }

    public Page<Post> findByTagsActived(Collection<String> tags, int page, int pageSize) {
        Page<Post> posts = postRepository.findByTagsAndStatus(tags, PostStatus.actived,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
        return posts;
    }

    public Page<Post> findByCategory(String category, int page, int pageSize) {
        Page<Post> posts = postRepository.findByCategoryAndStatus(category, PostStatus.actived,
                new PageRequest(page, pageSize, Sort.Direction.DESC,"topTime", "lastTime"));
        return posts;
    }


    /**
     * 热门
     */
    public Page<Post> hot(int page, int pageSize) {
        return postRepository.findByStatus(PostStatus.actived, new PageRequest(page, pageSize, Sort.Direction.DESC, "topTime", "replyCount", "lastTime"));
    }

    /**
     * 优选
     */
    public Page<Post> perfect(int page, int pageSize) {
        return postRepository.findByStatusAndPerfectTrue(PostStatus.actived, new PageRequest(page, pageSize, Sort.Direction.DESC, "perfectTime", "lastTime"));
    }

    /**
     * 此刻
     */
    public Page<Post> findNow(int page, int pageSize) {
       return postRepository.findByStatus(PostStatus.actived, new PageRequest(page, pageSize, Sort.Direction.DESC, "lastTime"));
    }

    // 查询前5名
    @Cacheable(value = CACHES_REALTIME_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findTop5Now() {
        return postRepository.findTop5ByStatusOrderByLastTimeDesc(PostStatus.actived);
    }

    // 相关话题
    @Cacheable(value = CACHES_NAME, keyGenerator = "cacheKeyGenerator")
    public List<Post> findTop5RelatedPosts(Long postId) {
        List<Tag> tags = tagService.findByPostId(postId);
        List<Post> posts = postRepository.findTop5DistinctByIdNotAndTagsInAndStatusOrderByLastTimeDesc(postId, tags, PostStatus.actived);
        return posts;
    }


    // 添加Post
    public Post addPost(PostForm postForm, String username) {
        Post post = postFormAdapter.createPostFromPostForm(postForm, username);
        Post saved = postRepository.save(post);
        // saveToIndex(post);

        // 更新Profile
        profileService.addPostCount(post.getAuthor().getId()); // == 1成功
        return saved;
    }

    // update ReadCount
    @CachePut(value = CACHE_NAME, key = "#post.id")
    public void read(Post post) {
        post.setReadCount(post.getReadCount() + 1);
        postRepository.save(post);
    }

    public Page<Post> findByAuthorId(Long authorId, int page, int size) {
        return postRepository.findByAuthorId(authorId, new PageRequest(page, size, Sort.Direction.DESC, "updatedTime"));
    }

    public void deletePost(Long postId) {
        replyRepository.deleteByPostId(postId);
        postRepository.delete(postId);
    }
}
