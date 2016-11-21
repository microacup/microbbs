package me.micro.bbs.markdown;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * RenderedContent
 *
 * Created by microacup on 2016/11/21.
 */
@Getter
@Setter
public class RenderedContent {
    private List<User> atedUsers = new ArrayList<>();

    private String html;

    /**
     * 添加At 用户
     *
     * @param user
     */
    public void addUser(User user) {
        this.atedUsers.add(user);
    }
}
