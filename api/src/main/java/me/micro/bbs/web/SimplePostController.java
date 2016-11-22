package me.micro.bbs.web;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.PostForm;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.setting.Setting;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by microacup on 2016/11/22.
 */
@Controller
@RequestMapping("/simple")
public class SimplePostController {
    /**
     * 按照分类下的标签展示话题
     *
     * @param categoryId
     * @param tagId
     * @param model
     * @return
     */
    @GetMapping("/category/{categoryId}/tag/{tagId}")
    public String postsByTag(@PathVariable("categoryId") long categoryId,
                             @PathVariable("tagId") long tagId,
                             @RequestParam(defaultValue = "1") int page,
                             Model model) {
        Category activeCategory = categoryService.findOne(categoryId);
        if (activeCategory == null) return "site/404";

        Tag activeTag = tagService.findOne(tagId);
        if (activeTag == null) return "site/404";
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("activeTag", activeTag);

        List<Long> tagIds = new ArrayList<>(1);
        tagIds.add(tagId);
        Page<Post> posts = postService.findByTags(tagIds, page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);

        return "simple/index";
    }

    /**
     * 话题详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") long id, Model model) {
        Post post = postService.findOne(id);
        if (post == null) return "site/404";

        postService.updateReadCount();
        model.addAttribute("post", post);
        return "simple/post";
    }

    // 新增Post
    @GetMapping("/create")
    public String create(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagService.getTagsMap());
        model.addAttribute("postForm", new PostForm());
        return "simple/create";
    }


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;
}
