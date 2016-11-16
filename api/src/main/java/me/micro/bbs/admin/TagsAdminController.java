package me.micro.bbs.admin;

import com.google.common.collect.Maps;
import me.micro.bbs.category.Category;
import me.micro.bbs.category.CategoryForm;
import me.micro.bbs.category.support.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

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
        Map<Long, CategoryForm> dtos = Maps.newHashMapWithExpectedSize(categories.size());
        for (Category c : categories) {
            CategoryForm form = new CategoryForm();
            form.setCode(c.getCode());
            form.setTitle(c.getTitle());
            dtos.put(c.getId(), form);
        }
        model.addAttribute("categories", dtos);

        return "admin/tags";
    }

    @Autowired
    private CategoryService categoryService;
}
