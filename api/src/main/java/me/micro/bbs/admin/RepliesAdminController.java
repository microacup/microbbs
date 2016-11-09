package me.micro.bbs.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 回帖管理
 *
 * Created by microacup on 2016/11/9.
 */
@Controller
public class RepliesAdminController  {

    @GetMapping("/admin/replies")
    public String replies() {

        return "admin/replies";
    }

}
