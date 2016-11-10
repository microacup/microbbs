package me.micro.bbs.admin;

import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.reply.support.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 回复管理
 *
 * Created by microacup on 2016/11/9.
 */
@Controller
public class RepliesAdminController  {

    @GetMapping("/admin/replies")
    public String replies(@RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size, Model model) {
        model.addAttribute("actived", "replies");

        Page<Post> posts = postService.findAll(page, size);
        model.addAttribute("posts", posts);

        return "admin/replies";
    }


    @Autowired
    private PostService postService;

    @Autowired
    private ReplyService replyService;
}
