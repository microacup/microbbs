package me.micro.bbs.post.support;

import me.micro.bbs.markdown.ContentRenderer;
import me.micro.bbs.markdown.RenderedContent;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.PostForm;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.user.support.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 *
 * Created by microacup on 2016/11/11.
 */
@Service
public class PostFormAdapter {
    private static final int SUMMARY_LENGTH = 140;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostSummary postSummary;

    @Autowired
    private ContentRenderer renderer;

    public Post createPostFromPostForm(PostForm postForm, String username) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setCreatedTime(new Date());
        post.setUpdatedTime(post.getCreatedTime());
        post.setLastTime(post.getCreatedTime());
        post.setTags(postForm.getTags());
        post.setAuthor(userRepository.findByUsername(username));
        post.setReplyable(postForm.isReplyable());
        post.setStatus(postForm.getStatus());

        post.setContent(postForm.getContent());
        setPostProperties(post);
        return post;
    }

    //
    private void setPostProperties(Post post) {
        RenderedContent renderedContent = renderer.render(post.getContent());
        post.setRenderedContent(renderedContent.getHtml());
        summarize(post);
    }

    // 生成摘要
    public void summarize(Post post) {
        String renderedSummary = postSummary.forContent(post.getRenderedContent(), SUMMARY_LENGTH);
        post.setSummary(renderedSummary);
    }

    public Post updatePostFromReply(Reply reply) {
        Post post = reply.getPost();
        post.setLastReplyTime(reply.getCreatedTime());
        post.setLastTime(reply.getCreatedTime());
        post.setLastAuthorName(reply.getAuthor().getNick());
        post.setLastAuthor(reply.getAuthor().getId());
        post.setFloorCount(reply.getFloor());
        post.setReplyCount(post.getReplyCount() + 1);
        return post;
    }


}
