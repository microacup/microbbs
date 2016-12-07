package me.micro.bbs.post;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.tag.Tag;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PostForm
 *
 * Created by microacup on 2016/11/11.
 */
@Getter
@Setter
public class PostForm {
    @NotEmpty
    private String mode;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String summary;

    @NotEmpty
    private Set<Tag> tags = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    private boolean replyable = true;

    @NotNull
    private PostStatus status = PostStatus.actived;

    // 新建
    public PostForm() {
    }

    // 编辑
    public PostForm(Post post) {
        title = post.getTitle();
        content = post.getContent();
        summary = post.getSummary();
        tags = post.getTags();
        updatedTime = post.getUpdatedTime();
        replyable = post.getReplyable();
    }

}
