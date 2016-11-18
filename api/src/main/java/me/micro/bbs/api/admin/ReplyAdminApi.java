package me.micro.bbs.api.admin;

import me.micro.bbs.post.Post;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.reply.support.ReplyService;
import me.micro.bbs.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static me.micro.bbs.consts.Uris.API_ADMIN_POSTS_ID_REPLIES;

/**
 *
 *
 * Created by microacup on 2016/11/16.
 */
@RestController
public class ReplyAdminApi {
    // 帖子的回复
    @GetMapping(API_ADMIN_POSTS_ID_REPLIES)
    public ResponseEntity<Page<Reply>> replies(@PathVariable("postId") long postId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        size = size == 0 ? Setting.REPLY_PAGE_SIZE : size;
        Post post = new Post();
        post.setId(postId);
        Page<Reply> replies = replyService.findAllReplies(post, page - 1, size);
        return ResponseEntity.ok(replies);
    }

    @Autowired
    private ReplyService replyService;
}
