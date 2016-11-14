package me.micro.bbs.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


        return "admin/tags";
    }
}
