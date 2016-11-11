package me.micro.bbs.admin;

import me.micro.bbs.post.support.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PostsAdminController
 *
 * Created by microacup on 2016/11/8.
 */
@Controller
@RequestMapping("/admin/posts")
public class PostsAdminController {

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size, Model model) {
        model.addAttribute("actived", "posts");

        return "admin/posts";
    }

    @Autowired
    private PostService postService;

}
