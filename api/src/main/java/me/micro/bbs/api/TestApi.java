package me.micro.bbs.api;

import me.micro.bbs.consts.Uris;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务可用性判断
 *
 * Created by microacup on 2016/10/21.
 */
@RestController
@RequestMapping(Uris.TEST)
public class TestApi {

    // @see https://www.noisyfox.cn/45.html
    @RequestMapping(Uris.GENERATE_204)
    public ResponseEntity<?> generate_204() {
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = Uris.AVAILABLE, produces = Uris.APPLICATION_JSON)
    public String available() {
        return "{\"name\":\"api\", \"available\": \"ok\"}";
    }


    @RequestMapping(value = "authorize" ,produces = Uris.APPLICATION_JSON)
    public String authorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "{\"user\" :\"" + authentication.getName() + "\", \"success\":true}";
        }

        return "{\"success\":false}";
    }
}
