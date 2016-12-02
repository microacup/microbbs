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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Posts
 *
 * Created by microacup on 2016/11/3.
 */
@Controller
public class PostController extends BaseController {

    /**
     * 按分类展示话题
     *
     * @param category
     * @param model
     * @return
     */
    @GetMapping("/category/{category}")
    public String postsByCategory(@PathVariable("category") String category,
                                  @RequestParam(defaultValue = "1") int page,
                                  Model model) {
        Category activeCategory = categoryService.findByCode(category);
        if (activeCategory == null) return "site/404";

        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("categories", categories);

        List<Tag> tags = tagService.findByCategory(activeCategory.getCode());
        if(tags.isEmpty()) return "site/404";

        Tag activeTag = getDefaultTag(activeCategory);
        model.addAttribute("activeTag", activeTag);
        model.addAttribute("tags", tags);

        Page<Post> posts = postService.findByCategory(category, page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);
        super.injectMode(model);

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
     * 按照分类下的标签展示话题
     *
     * @param category
     * @param tag
     * @param model
     * @return
     */
    @GetMapping("/category/{category}/tag/{tag}")
    public String postsByTag(@PathVariable("category") String category,
                             @PathVariable("tag") String tag,
                             @RequestParam(defaultValue = "1") int page,
                             Model model) {
        Category activeCategory = categoryService.findByCode(category);
        if (activeCategory == null) return "site/404";

        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";
        model.addAttribute("categories", categories);

        Tag activeTag = tagService.findByCode(tag);
        if (activeTag == null) return "site/404";
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("activeTag", activeTag);

        List<Tag> tags = tagService.findByCategory(activeCategory.getCode());
        model.addAttribute("tags", tags);

        List<String> tagCodes = new ArrayList<>(1);
        tagCodes.add(tag);
        Page<Post> posts = postService.findByTags(tagCodes, page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);
        super.injectMode(model);

        return "site/index";
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
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";
        model.addAttribute("categories", categories);

        Post post = postService.findOne(id);
        if (post == null) return "site/404";

        postService.read(post);
        super.injectMode(model);
        model.addAttribute("post", post);
        return "site/post";
    }

    // 新增Post
    @GetMapping("/create")
    public String create(Model model) {
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        super.injectMode(model);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagService.getTagsMap());
        model.addAttribute("postForm", new PostForm());
        return "site/create";
    }

    /**
     * 新增Post
     *
     * @param principal
     * @param postForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/post/create")
    public String createPost(Principal principal, @Valid PostForm postForm, BindingResult bindingResult, Model model) {
        String name = principal.getName();
        Post post = postService.addPost(postForm, name);
        String mode = postForm.getMode();
        if (MODE_SIMPLE.equalsIgnoreCase(mode)) {
            return "redirect:/simple/post/" + post.getId();
        }
        return "redirect:/post/" + post.getId();
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
