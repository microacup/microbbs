package me.micro.bbs.api;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.setting.Setting;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TagApi
 *
 * Created by microacup on 2016/11/3.
 */
@RestController
public class TagApi {

    @Autowired
    private TagService tagService;

    // 按分类找标签
    @GetMapping(Uris.API_CATEGORIES_TAGS)
    public ResponseEntity<List<Tag>> tags(@PathVariable("code") String code) {
        List<Tag> tags = tagService.findByCategory(code);
        return ResponseEntity.ok(tags);
    }

    // 热门标签
    @GetMapping(Uris.API_HOT_TAGS)
    public ResponseEntity<List<Tag>> hotTags() {
        List<Tag> tags = tagService.findTopN(Setting.HOT_TAGS);
        return ResponseEntity.ok(tags);
    }



}
