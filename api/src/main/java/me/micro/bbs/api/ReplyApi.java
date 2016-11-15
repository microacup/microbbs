package me.micro.bbs.api;

import me.micro.bbs.client.Result;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.reply.ReplyForm;
import me.micro.bbs.reply.support.ReplyService;
import me.micro.bbs.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static me.micro.bbs.consts.Uris.API_POSTS_ID_REPLIES;
import static me.micro.bbs.consts.Uris.API_REPLIES;
import static me.micro.bbs.consts.Uris.API_REPLIES_REPLY_ID_REPLIES;

/**
 * ReplyApi
 *
 * Created by microacup on 2016/11/8.
 */
@RestController
public class ReplyApi {

    // 帖子的回复
    @GetMapping(API_POSTS_ID_REPLIES)
    public ResponseEntity<Page<Reply>> replies(@PathVariable("postId") long postId, @RequestParam(defaultValue = "0") int page) {
        Post post = new Post();
        post.setId(postId);
        Page<Reply> replies = replyService.findReplies(post, page, Setting.REPLY_PAGE_SIZE);
        return ResponseEntity.ok(replies);
    }

    // 回复的回复
    @GetMapping(API_REPLIES_REPLY_ID_REPLIES)
    public ResponseEntity<Page<Reply>> repliesByReply(@PathVariable("replyId") long replyId, @RequestParam(defaultValue = "0") int page) {
        Page<Reply> replies = replyService.findRepliesByReplyAndStatus(replyId, page, Setting.REPLY_PAGE_SIZE);
        return ResponseEntity.ok(replies);
    }

    // 发布回复
    @PostMapping(API_REPLIES)
    public Result<Reply> createReply(Principal principal, @Valid @RequestBody ReplyForm replyForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        }

        String name = principal.getName();
        Reply reply = replyService.addReply(replyForm, name);
        return Result.ok(reply);
    }

    @Autowired
    private ReplyService replyService;

    @Autowired
    private PostService postService;

}
