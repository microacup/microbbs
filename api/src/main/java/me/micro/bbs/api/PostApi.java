package me.micro.bbs.api;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.setting.Setting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 按照标签找帖子
     *
     * @param tags
     * @param page
     * @return
     */
    @GetMapping(Uris.API_POSTS)
    public ResponseEntity<Page<Post>> postsByTags(@RequestParam(value = "tags", required = false) String tags,
                                                  @RequestParam(defaultValue = "0") int page) {
        Page<Post> posts = null;
        if (!StringUtils.isBlank(tags)) {
            String[] strings = tags.split(",");
            List<Long> tagList = new ArrayList<>(strings.length);
            for (String tag : strings) {
                tagList.add(Long.parseLong(tag));
            }
            posts = postService.findByTags(tagList, page, Setting.PAGE_SIZE);
            return ResponseEntity.ok(posts);
        } else {
            posts = postService.findAll(page, Setting.PAGE_SIZE);
        }

        return ResponseEntity.ok(posts);
    }

    /**
     * 按照分类找帖子
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
     * 帖子详情
     *
     * @param id
     * @return
     */
    @GetMapping(Uris.API_POSTS + Uris.ID)
    public ResponseEntity<Post> one(@PathVariable("id") long id) {
        Post post = postService.findOne(id);
        if (post == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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


}
