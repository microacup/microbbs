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
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单模式
 *
 * Created by microacup on 2016/11/22.
 */
@Controller
@RequestMapping("/simple")
public class SimplePostController extends BaseController {

    @GetMapping
    public String index(HttpServletRequest request) {
        String entry = (String) WebUtils.getSessionAttribute(request, "simple-entry");
        if (entry != null) {
            return "redirect:" + entry;
        }

        return "redirect:/404";
    }

    /**
     * 按照分类下的标签展示话题
     *
     * @param category
     * @param model
     * @return
     */
    @GetMapping("/category/{category}")
    public String postsByCategory(HttpServletRequest request,
                             @PathVariable("category") String category,
                             @RequestParam(defaultValue = "1") int page,
                             Model model) {
        super.injectMode(model);

        // 简单的权限校验，更合理的方式是使用Role & Permission，请在forward入口处清空simple-category
        HttpSession session = request.getSession();
        String lastCategory = (String) session.getAttribute("simple-category");
        if (lastCategory != null && !lastCategory.equals(category)) {
            return "site/404";
        }
        session.setAttribute("simple-category", category);
        session.setAttribute("simple-entry", request.getRequestURL().toString());

        Category activeCategory = categoryService.findByCode(category);
        if (activeCategory == null) return "site/404";

        model.addAttribute("activeCategory", activeCategory);
        Page<Post> posts = postService.findByCategory(category, page - 1, Setting.PAGE_SIZE);
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("currentPage", page);
        Tag activeTag = getDefaultTag(activeCategory);
        model.addAttribute("activeTag", activeTag);

        return "simple/index";
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
    public String postsByTag(HttpServletRequest request,
                             @PathVariable("category") String category,
                             @PathVariable("tag") String tag,
                             @RequestParam(defaultValue = "1") int page,
                             Model model) {
        super.injectMode(model);

        // 简单的权限校验，更合理的方式是使用Role & Permission，请在forward入口处清空simple-category
        HttpSession session = request.getSession();
        String lastCategory = (String) session.getAttribute("simple-category");
        if (lastCategory != null && !lastCategory.equals(category)) {
            return "site/404";
        }

        session.setAttribute("simple-category", category);
        session.setAttribute("simple-tag", tag);
        session.setAttribute("simple-entry", request.getRequestURL().toString());

        Category activeCategory = categoryService.findByCode(category);
        if (activeCategory == null) return "site/404";

        Tag activeTag = tagService.findByCode(tag);
        if (activeTag == null) return "site/404";
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("activeTag", activeTag);

        List<String> tagCodes = new ArrayList<>(1);
        tagCodes.add(tag);
        Page<Post> posts = postService.findByTags(tagCodes, page - 1, Setting.PAGE_SIZE);
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
        super.injectMode(model);
        Post post = postService.findOne(id);
        if (post == null) return "site/404";

        postService.read(post);

        model.addAttribute("backable", true);
        model.addAttribute("title", "返回");
        model.addAttribute("post", post);
        return "simple/post";
    }

    // 新增Post
    @GetMapping("/create")
    public String create(Model model) {
        super.injectMode(model);
        List<Category> categories = categoryService.findAll();
        if (categories == null) return "site/404";

        model.addAttribute("backable", true);
        model.addAttribute("title", "返回");
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

    @Override
    protected String getMode() {
        return MODE_SIMPLE;
    }
}
