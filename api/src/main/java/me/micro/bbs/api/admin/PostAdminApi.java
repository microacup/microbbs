package me.micro.bbs.api.admin;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.setting.Setting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by microacup on 2016/11/18.
 */
@RestController
public class PostAdminApi {
    @Autowired
    private PostService postService;

    /**
     * 按照标签找话题
     *
     * @param tags
     * @param page
     * @return
     */
    @GetMapping(Uris.API_ADMIN_POSTS)
    public ResponseEntity<Page<Post>> postsByTags(@RequestParam(value = "tags", required = false) String tags,
                                                  @RequestParam(defaultValue = "0") int size,
                                                  @RequestParam(defaultValue = "1") int page) {
        size = size == 0 ? Setting.PAGE_SIZE : size;
        Page<Post> posts = null;
        if (!StringUtils.isBlank(tags)) {
            String[] strings = tags.split(",");
            List<Long> tagList = new ArrayList<>(strings.length);
            for (String tag : strings) {
                tagList.add(Long.parseLong(tag));
            }
            posts = postService.findByTags(tagList, page - 1, size);
            return ResponseEntity.ok(posts);
        } else {
            posts = postService.findAll(page - 1, size);
        }

        return ResponseEntity.ok(posts);
    }
}
