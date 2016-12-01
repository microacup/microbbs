package me.micro.bbs.api;

import me.micro.bbs.client.Result;
import me.micro.bbs.consts.Uris;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.setting.Setting;
import me.micro.bbs.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Post API
 *
 * Created by microacup on 2016/11/2.
 */
@RestController
public class PostApi {

    @Autowired
    private PostService postService;

    /**
     * 按照标签找话题
     *
     * @param tags
     * @param page
     * @return
     */
    @GetMapping(Uris.API_POSTS)
    public ResponseEntity<Page<Post>> postsByTags(@RequestParam(value = "tags", required = false) String tags,
                                                  @RequestParam(defaultValue = "0") int pageSize,
                                                  @RequestParam(defaultValue = "0") int page) {
        pageSize = pageSize == 0 ? Setting.PAGE_SIZE : pageSize;
        Page<Post> posts = null;
        if (!StringUtils.isBlank(tags)) {
            String[] strings = tags.split(",");
            List<Long> tagList = new ArrayList<>(strings.length);
            for (String tag : strings) {
                tagList.add(Long.parseLong(tag));
            }
            posts = postService.findByTagsActived(tagList, page, pageSize);
            return ResponseEntity.ok(posts);
        } else {
            posts = postService.findActived(page, pageSize);
        }

        return ResponseEntity.ok(posts);
    }

    /**
     * 按照分类找话题
     *
     * @param categoryId
     * @param page
     * @return
     */
    @GetMapping(Uris.API_CATEGORIES_POSTS)
    public ResponseEntity<Page<Post>> postsByCategory(@PathVariable(value = "categoryId") long categoryId,
                                                      @RequestParam(defaultValue = "0") int page) {
        Page<Post> posts = postService.findByCategoryId(categoryId, page, Setting.PAGE_SIZE);
        return ResponseEntity.ok(posts);
    }

    /**
     * 话题详情
     *
     * @param id
     * @return
     */
    @GetMapping(Uris.API_POSTS + Uris.ID)
    public ResponseEntity<Post> one(@PathVariable("id") long id) {
        Post post = postService.findOne(id);
        if (post == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // TODO readCount + 1
        return ResponseEntity.ok(post);
    }

    /**
     * 此刻
     *
     * @param page
     * @return
     */
    @GetMapping(Uris.API_POSTS_NOW)
    public ResponseEntity<Page<Post>> now(@RequestParam(defaultValue = "0") int page) {
        Page<Post> posts = postService.findNow(page, Setting.PAGE_SIZE);
        return ResponseEntity.ok(posts);
    }

    /**
     * 前5名新话题
     *
     * @return
     */
    @GetMapping(Uris.API_POSTS_NOW_TOP5)
    public ResponseEntity<List<Post>> nowTop5() {
        return ResponseEntity.ok(postService.findTop5Now());
    }

    /**
     * 相关话题Top5
     *
     * @return
     */
    @GetMapping(Uris.API_POSTS_RELATED_TOP5)
    public ResponseEntity<List<Post>> relatedPostsTop5(@PathVariable("postId") long postId) {
        List<Post> posts = postService.findTop5RelatedPosts(postId);
        return ResponseEntity.ok(posts);
    }

    /**
     * 俺的帖子
     *
     * @return
     */
    @GetMapping(Uris.API_POSTS_ME)
    public Result<Page<Post>> findMyPost(@RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Page<Post> posts = postService.findByAuthor(user, page, Setting.PAGE_SIZE);
            return Result.ok(posts);
        }

        return Result.error(HttpStatus.UNAUTHORIZED.value(), "请先登录");
    }

    /**
     * 别人家的帖子
     *
     * @return
     */
    @GetMapping(Uris.API_USERS_USER_ID_POSTS)
    public Result<Page<Post>> findWhoesPost(@PathVariable("userId")Long userId, @RequestParam(defaultValue = "0") int page) {
        Page<Post> posts = postService.findByAuthorId(userId, page, Setting.PAGE_SIZE);
        return Result.ok(posts);
    }

}
