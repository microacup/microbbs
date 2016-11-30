package me.micro.bbs.api;

import com.docuverse.identicon.IdenticonCache;
import com.docuverse.identicon.IdenticonRenderer;
import com.docuverse.identicon.IdenticonUtil;
import com.docuverse.identicon.MemoryIdenticonCache;
import com.docuverse.identicon.NineBlockIdenticonRenderer2;
import me.micro.bbs.client.Result;
import me.micro.bbs.user.User;
import me.micro.bbs.user.UserProfile;
import me.micro.bbs.user.support.ProfileService;
import me.micro.bbs.user.support.UserService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static me.micro.bbs.consts.Uris.API_AVATAR;
import static me.micro.bbs.consts.Uris.API_PROFILE;
import static me.micro.bbs.consts.Uris.API_USERS_USER_ID_AVATAR;
import static me.micro.bbs.consts.Uris.API_USERS_USER_ID_PROFILE;

/**
 *
 *
 * Created by microacup on 2016/11/22.
 */
@RestController
public class ProfileApi {
    private static final String IDENTICON_IMAGE_FORMAT = "PNG";
    private static final long DEFAULT_IDENTICON_EXPIRES_IN_MILLIS = 24 * 60 * 60 * 1000;
    private static final String IDENTICON_IMAGE_MIMETYPE = "image/png";

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    private IdenticonCache cache = new MemoryIdenticonCache();
    private IdenticonRenderer renderer = new NineBlockIdenticonRenderer2();
    private long identiconExpiresInMillis = DEFAULT_IDENTICON_EXPIRES_IN_MILLIS;

    @GetMapping(API_PROFILE)
    Result<UserProfile> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Result.error(HttpStatus.SC_UNAUTHORIZED, "请先登录");
        }
        User user = (User) authentication.getPrincipal();

        UserProfile profile = profileService.profile(user.getId());
        return Result.ok(profile);
    }

    @GetMapping(API_USERS_USER_ID_PROFILE)
    Result<UserProfile> profile(@PathVariable("userId") Long userId) {
        UserProfile profile = profileService.profile(userId);
        return Result.ok(profile);
    }

    @GetMapping(API_AVATAR)
    public void meAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof User)) {
            response.sendRedirect("/404");
            return;
        }

        User user = (User) authentication.getPrincipal();

        avatar(request, response, user);
    }

    @GetMapping(API_USERS_USER_ID_AVATAR)
    public void avatar(@PathVariable("userId") Long userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO 查询用户上传的头像
        User user = userService.findOne(userId);
        if (user == null) {
            response.sendRedirect("/404");
            return;
        }

        avatar(request, response, user);
    }

    private void avatar(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        int code = (user.getUsername() + user.getId()).hashCode();
        int size = 128;
        int version = 1;

        String identiconETag = IdenticonUtil.getIdenticonETag(code, size, version);
        String requestETag = request.getHeader("If-None-Match");

        if (requestETag != null && requestETag.equals(identiconETag)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } else {
            byte[] imageBytes = null;

            // retrieve image bytes from either cache or renderer
            if (cache == null || (imageBytes = cache.get(identiconETag)) == null) {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                RenderedImage image = renderer.render(code, size);
                ImageIO.write(image, IDENTICON_IMAGE_FORMAT, byteOut);
                imageBytes = byteOut.toByteArray();
                if (cache != null) {
                    cache.add(identiconETag, imageBytes);
                }
            }

            // set ETag and, if code was provided, Expires header
            response.setHeader("ETag", identiconETag);
            long expires = System.currentTimeMillis() + identiconExpiresInMillis;
            response.addDateHeader("Expires", expires);

            // return image bytes to requester
            response.setContentType(IDENTICON_IMAGE_MIMETYPE);
            response.setContentLength(imageBytes.length);
            response.getOutputStream().write(imageBytes);
        }
    }
}
