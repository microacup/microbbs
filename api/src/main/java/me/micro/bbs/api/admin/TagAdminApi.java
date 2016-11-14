package me.micro.bbs.api.admin;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TagAdminApi
 *
 * Created by microacup on 2016/11/14.
 */
@RestController
@RequestMapping(Uris.API_ADMIN_TAGS)
public class TagAdminApi {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> categories() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }

}
