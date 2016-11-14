package me.micro.bbs.api.admin;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.CategoryForm;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.client.Result;
import me.micro.bbs.consts.Uris;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CategoryAdminApi
 *
 * Created by microacup on 2016/11/14.
 */
@RestController
@RequestMapping(Uris.API_ADMIN_CATEGORIES)
public class CategoryAdminApi {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Category>> categories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> add(@RequestBody CategoryForm form) {
        Category category = categoryService.addCategory(form);
        return ResponseEntity.ok(category);
    }

    @PutMapping(Uris.ID)
    public ResponseEntity<Category> update(@PathVariable("id") long id, @RequestBody CategoryForm form) {
        Category category = categoryService.findOne(id);
        Category saved = categoryService.updateCategory(category, form);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping(Uris.ID)
    public Result<?> delete(@PathVariable("id") long id) {
        // 检查是否有tags关联
        Long count = tagService.countByCategoryId(id);
        if (count != null && count > 0) {
            return new Result(HttpStatus.NOT_ACCEPTABLE.value()).setMsg("已经关联了标签，不能删除");
        }

        categoryService.deleteCategory(id);
        return Result.ok(null);
    }

}
