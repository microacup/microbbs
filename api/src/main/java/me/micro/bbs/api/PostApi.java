package me.micro.bbs.api;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Post API
 *
 * Created by microacup on 2016/11/2.
 */
@RestController
@RequestMapping(Uris.API_POSTS)
public class PostApi {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> posts(@RequestParam(value = "tags", required = false) String tags) {
        List<Post> posts = Collections.emptyList();
        if (!StringUtils.isBlank(tags)) {
            String[] strings = tags.split("\\|");
            List<Long> tagList = new ArrayList<>(strings.length);
            for (String tag : strings) {
                tagList.add(Long.parseLong(tag));
            }
            posts = postService.findByTags(tagList);
            return ResponseEntity.ok(posts);
        } else {
            posts = postService.findAll();
        }

        return ResponseEntity.ok(posts);
    }


    @GetMapping(Uris.ID)
    public ResponseEntity<Post> one(@PathVariable("id") long id) {
        Post post = postService.findOne(id);
        if (post == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(post);
    }


}
