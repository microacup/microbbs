package me.micro.bbs.web;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.setting.Setting;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 探索频道
 *
 * Created by microacup on 2016/11/3.
 */
@Controller
public class ExploreController extends BaseController {

    // 热门
    @GetMapping("/hot")
    public String hot(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "hot");
        model.addAttribute("activedExplorer", "热门");

        Page<Post> posts = postService.hot(page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        super.injectMode(model);

        return "site/index";
    }

    @GetMapping("/now")
    public String now(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "now");
        model.addAttribute("activedExplorer", "此刻");

        Page<Post> posts = postService.findNow(page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        super.injectMode(model);
        return "site/index";
    }

    @GetMapping("/perfect")
    public String perfect(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) return "site/404";
        model.addAttribute("categories", categories);
        model.addAttribute("actived", "perfect");
        model.addAttribute("activedExplorer", "优选");

        Page<Post> posts = postService.perfect(page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        super.injectMode(model);
        return "site/index";
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Override
    protected String getMode() {
        return MODE_NORMAL;
    }
}
