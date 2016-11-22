package me.micro.bbs.api;

import me.micro.bbs.client.Result;
import me.micro.bbs.user.User;
import me.micro.bbs.user.UserProfile;
import me.micro.bbs.user.support.ProfileService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * Created by microacup on 2016/11/22.
 */
@RestController
public class ProfileApi {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    @RequestMapping("/api/profile")
    Result<UserProfile> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Result.error(HttpStatus.SC_UNAUTHORIZED, "请先登录");
        }
        User user = (User) authentication.getPrincipal();

        UserProfile profile = profileService.profile(user.getId());
        return Result.ok(profile);
    }

    @GetMapping
    @RequestMapping("/api/users/{userId}/profile")
    Result<UserProfile> profile(@PathVariable("userId") Long userId) {
        UserProfile profile = profileService.profile(userId);
        return Result.ok(profile);
    }
}
