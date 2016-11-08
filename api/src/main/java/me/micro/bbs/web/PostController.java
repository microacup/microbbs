package me.micro.bbs.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.micro.bbs.category.Category;
import me.micro.bbs.category.support.CategoryService;
import me.micro.bbs.post.Post;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String postsByCategory(@PathVariable("categoryId") long categoryId,
                                  @RequestParam(defaultValue = "0") int page,
                                  Model model) {
        Category activeCategory = categoryService.findOne(categoryId);
        if (activeCategory == null) return "site/404";

        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("categories", categories);

        List<Tag> tags = tagService.findByCategory(activeCategory.getId());
        if(tags.isEmpty()) return "site/404";

        Tag activeTag = getDefaultTag(activeCategory);
        model.addAttribute("activeTag", activeTag);
        model.addAttribute("tags", tags);

        Page<Post> posts = postService.findByCategoryId(categoryId, page, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPages", posts.getTotalPages());

        return "site/index";
    }

    private Tag getDefaultTag(Category activeCategory) {
        Tag activeTag = new Tag();
        activeTag.setId(-1L);
        activeTag.setTitle("全部");
        activeTag.setCategory(activeCategory);
        return activeTag;
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
    public String postsByTag(@PathVariable("categoryId") long categoryId,
                             @PathVariable("tagId") long tagId,
                             @RequestParam(defaultValue = "0") int page,
                             Model model) {
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
        Page<Post> posts = postService.findByTags(tagIds, page, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", posts.getTotalPages());

        return "site/index";
    }

    @GetMapping("/tags/{tagId}")
    public String postByTag(@PathVariable("tagId") long tagId) {
        Tag activeTag = tagService.findOne(tagId);
        if (activeTag == null) return "site/404";

        return "site/index";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") long id, Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";
        model.addAttribute("categories", categories);

        Post post = postService.findOne(id);
        if (post == null) return "site/404";
        model.addAttribute("post", post);

        return "site/post";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";
        model.addAttribute("categories", categories);

        Map<Category, List<Tag>> tags = Maps.newHashMapWithExpectedSize(categories.size());
        List<Tag> list = tagService.findAll();
        for (Tag tag : list) {
            List<Tag> ts = tags.get(tag.getCategory());
            if (ts == null) {
                ts = Lists.newArrayList();
                tags.put(tag.getCategory(), ts);
            }
            ts.add(tag);
        }

        model.addAttribute("tags", tags);
        return "site/create";
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;
}
