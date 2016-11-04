package me.micro.bbs.web;

import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.tag.Tag;
import me.micro.bbs.tag.support.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Posts
 *
 * Created by microacup on 2016/11/3.
 */
@Controller
public class PostController {

    /**
     * 按分类展示帖子
     *
     * @param categoryId
     * @param model
     * @return
     */
    @GetMapping("/category/{categoryId}")
    public String postsByCategory(@PathVariable("categoryId") long categoryId, Model model) {
        Category activeCategory = categoryService.findOne(categoryId);
        if (activeCategory == null) return "site/404";

        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("categories", categories);

        List<Tag> tags = tagService.findByCategory(activeCategory.getId());
        if(tags.isEmpty()) return "site/404";

        Tag activeTag = tags.get(0);
        model.addAttribute("activeTag", activeTag);
        model.addAttribute("tags", tags);

        List<Post> posts = postService.findByCategoryId(categoryId);
        model.addAttribute("posts", posts);

        return "site/index";
    }

    /**
     * 按照分类下的标签展示帖子
     *
     * @param categoryId
     * @param tagId
     * @param model
     * @return
     */
    @GetMapping("/category/{categoryId}/tag/{tagId}")
    public String postsByTag(@PathVariable("categoryId") long categoryId, @PathVariable("tagId") long tagId, Model model) {
        Category activeCategory = categoryService.findOne(categoryId);
        if (activeCategory == null) return "site/404";

        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";
        model.addAttribute("categories", categories);

        Tag activeTag = tagService.findOne(tagId);
        if (activeTag == null) return "site/404";
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("activeTag", activeTag);

        List<Tag> tags = tagService.findByCategory(activeCategory.getId());
        model.addAttribute("tags", tags);

        List<Long> tagIds = new ArrayList<>(1);
        tagIds.add(tagId);
        List<Post> posts = postService.findByTags(tagIds);
        model.addAttribute("posts", posts);

        return "site/index";
    }

    @GetMapping("/tags/{tagId}")
    public String postByTag(@PathVariable("tagId") long tagId) {
        Tag activeTag = tagService.findOne(tagId);
        if (activeTag == null) return "site/404";


        return "site/index";
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;
}