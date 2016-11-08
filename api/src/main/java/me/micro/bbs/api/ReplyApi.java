package me.micro.bbs.api;

import me.micro.bbs.consts.Uris;
import me.micro.bbs.post.support.PostService;
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

/**
 * ReplyApi
 *
 * Created by microacup on 2016/11/8.
 */
@RestController
public class ReplyApi {

    @GetMapping(Uris.API_POSTS_ID_REPLIES)
    public ResponseEntity<Page<Reply>> replies(@PathVariable("postId") long postId, @RequestParam(defaultValue = "0") int page) {
        Page<Reply> replies = replyService.findReplies(postId, page, Setting.REPLY_PAGE_SIZE);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/api/replies/{replyId}/replies")
    public ResponseEntity<Page<Reply>> repliesByReply(@PathVariable("replyId") long replyId, @RequestParam(defaultValue = "0") int page) {
        Page<Reply> replies = replyService.findRepliesByReplyAndStatus(replyId, page, Setting.REPLY_PAGE_SIZE);
        return ResponseEntity.ok(replies);
    }

    @Autowired
    private ReplyService replyService;

    @Autowired
    private PostService postService;

}
