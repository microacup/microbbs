package me.micro.bbs.admin;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 标签管理
 *
 * Created by microacup on 2016/11/14.
 */
@Controller
@RequestMapping("/admin/tags")
public class TagsAdminController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("actived", "tags");

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "admin/tags";
    }

    @Autowired
    private CategoryService categoryService;
}
