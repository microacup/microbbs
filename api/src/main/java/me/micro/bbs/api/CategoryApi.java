package me.micro.bbs.api;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.consts.Uris;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CategoryApi
 *
 * Created by microacup on 2016/11/3.
 */
@RestController
@RequestMapping(Uris.API_CATEGORIES)
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> categories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

}
