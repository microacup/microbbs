package me.micro.bbs.web;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 探索频道
 *
 * Created by microacup on 2016/11/3.
 */
@Controller
public class ExploreController {

    @GetMapping("/explore")
    public String explore(Model model) {
        return "forward:/now";
    }

    @GetMapping("/now")
    public String now(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "now");


        return "site/index";
    }

    @GetMapping("/perfect")
    public String perfect(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "perfect");

        return "site/index";
    }

    @GetMapping("/hot")
    public String hot(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "hot");


        return "site/index";
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;
}
