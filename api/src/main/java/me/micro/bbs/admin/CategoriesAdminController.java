package me.micro.bbs.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分类管理
 *
 * Created by microacup on 2016/11/14.
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoriesAdminController {

    @GetMapping
    public String index(Model model) {

        model.addAttribute("actived", "categories");


        return "admin/categories";
    }
}
