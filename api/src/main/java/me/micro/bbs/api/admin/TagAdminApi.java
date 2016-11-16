package me.micro.bbs.api.admin;

import me.micro.bbs.client.Result;
import me.micro.bbs.consts.Uris;
import me.micro.bbs.exception.MicroException;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.TagForm;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping
    public Result<Tag> add(@Valid @RequestBody TagForm tagForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result<Tag>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("添加失败");
        }

        Tag tag = tagService.addTag(tagForm);
        if (tag == null) {
            return new Result<Tag>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("添加失败");
        }

        return Result.ok(tag);
    }

    @PutMapping(Uris.ID)
    public Result<Tag> update(@PathVariable("id") long id, @Valid @RequestBody TagForm tagForm, BindingResult bindingResult) {
        Tag one = tagService.findOne(id);
        if (one == null) {
            return new Result<Tag>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("更新失败, 标签不存在");
        }

        if (bindingResult.hasErrors()) {
            return new Result<Tag>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("更新失败");
        }

        Tag tag = tagService.update(one, tagForm);
        if (tag == null) {
            return new Result<Tag>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("更新失败");
        }

        return Result.ok(tag);
    }

    @DeleteMapping(Uris.ID)
    public Result<?> delete(@PathVariable("id") long id) {
        try {
            tagService.delete(id);
        } catch (MicroException e) {
            return new Result<>(HttpStatus.NOT_ACCEPTABLE.value()).setMsg(e.getMessage());
        }

        return Result.ok(null);
    }

}
