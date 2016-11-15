package me.micro.bbs.reply.support;

import me.micro.bbs.markdown.ContentRenderer;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostRepository;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.reply.ReplyForm;
import me.micro.bbs.user.support.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 *
 * Created by microacup on 2016/11/15.
 */
@Service
public class ReplyFormAdpater {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentRenderer renderer;

    public Reply createReplyFromReplyForm(ReplyForm replyForm, String username) {
        Reply reply = new Reply();
        reply.setCreatedTime(new Date());
        reply.setUpdatedTime(reply.getCreatedTime());
        reply.setAuthor(userRepository.findByUsername(username));
        reply.setStatus(replyForm.getStatus());

        // Post不走缓存
        Post post = postRepository.findOne(replyForm.getPostId());
        reply.setPost(post);
        reply.setFloor(post.getFloorCount() + 1);

        Long replyId = replyForm.getReplyId();
        if (replyId != null) {
            reply.setReply(replyService.findOne(replyId));
        }

        reply.setContent(replyForm.getContent());
        reply.setRenderedContent(renderer.render(replyForm.getContent()));
        return reply;
    }

    public Post updatePostFromReply(Reply reply) {
        Post post = reply.getPost();
        post.setLastReplyTime(reply.getCreatedTime());
        post.setLastTime(reply.getCreatedTime());
        post.setLastAuthorName(reply.getAuthor().getName());
        post.setLastAuthor(reply.getAuthor().getId());
        post.setFloorCount(reply.getFloor());
        post.setReplyCount(post.getReplyCount() + 1);
        return post;
    }

}
