package me.micro.bbs.web;

import me.micro.bbs.user.User;
import me.micro.bbs.user.UserProfile;
import me.micro.bbs.user.support.ProfileService;
import me.micro.bbs.user.support.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 个人信息
 *
 * Created by microacup on 2016/12/1.
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/me")
    public String me(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = (User) authentication.getPrincipal();

        UserProfile profile = profileService.profile(me.getId());
        model.addAttribute("isMe", true);
        model.addAttribute("user", me);
        model.addAttribute("profile", profile);

        return "site/profile";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable("userId") Long userId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = (User) authentication.getPrincipal();
        User user = me;
        boolean isMe = me.getId().equals(userId);
        if (!isMe) {
            user = userService.findOne(userId);
        }

        // 个人资料
        UserProfile profile = profileService.profile(userId);

        model.addAttribute("isMe", isMe);
        model.addAttribute("user", user);
        model.addAttribute("profile", profile);

        return "site/profile";
    }

}
