package me.micro.bbs.reply;

import lombok.Getter;
import lombok.Setter;
import me.micro.bbs.enums.PostStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * ReplyForm
 *
 * Created by microacup on 2016/11/15.
 */
@Getter
@Setter
public class ReplyForm {
    @NotEmpty
    private String content;

    @NotNull
    private Long postId;

    private Long replyId;

    private String from;

    @NotNull
    private PostStatus status = PostStatus.actived;

    /*@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;*/
}
