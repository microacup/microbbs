package me.micro.bbs.reply.support;

import com.google.common.collect.Lists;
import me.micro.bbs.enums.MessageType;
import me.micro.bbs.enums.PostStatus;
import me.micro.bbs.markdown.ContentRenderer;
import me.micro.bbs.markdown.RenderedContent;
import me.micro.bbs.message.Message;
import me.micro.bbs.message.support.MessageService;
import me.micro.bbs.post.Post;
import me.micro.bbs.post.support.PostFormAdapter;
import me.micro.bbs.post.support.PostRepository;
import me.micro.bbs.post.support.PostService;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.reply.ReplyForm;
import me.micro.bbs.user.User;
import me.micro.bbs.user.support.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ReplyService
 *
 * Created by microacup on 2016/11/8.
 */
@Service
@Transactional
public class ReplyService {
    public static final String CACHES_NAME = "cache.replies";
    public static final String CACHE_NAME = "cache.reply";
    public static final Class<?> CACHE_TYPE = Reply.class;


    public Reply findOne(Long id) {
        return replyRepository.findOne(id);
    }


    // 按照话题找回复
    public Page<Reply> findReplies(Post post, int page, int pageSize) {
        Page<Reply> replies = replyRepository.findByPostAndStatus(post,
                PostStatus.actived,
                new PageRequest(page, pageSize, Sort.Direction.ASC, "floor"));
        return replies;
    }

    // 按照话题找回复
    public Page<Reply> findAllReplies(Post post, int page, int pageSize) {
        Page<Reply> replies = replyRepository.findByPost(post,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "topTime", "adoptTime", "perfectTime", "floor"));
        return replies;
    }

    // 回复的回复
    public Page<Reply> findRepliesByReplyAndStatus(Long replyId, int page, int pageSize) {
        Reply reply = new Reply();
        reply.setId(replyId);
        Page<Reply> replies = replyRepository.findByReplyAndStatus(reply, PostStatus.actived, new PageRequest(page, pageSize, Sort.Direction.ASC, "floor"));
        return replies;
    }

    @CacheEvict(value = PostService.CACHE_NAME, key = "#replyForm.postId")
    public Reply addReply(ReplyForm replyForm, String username) {
        User author = userRepository.findByUsername(username);
        Reply reply = createReply(replyForm, author);

        // 渲染markdown
        RenderedContent renderedContent = renderer.render(replyForm.getContent());
        String renderedHtml = renderedContent.getHtml();
        reply.setRenderedContent(renderedHtml);
        replyRepository.save(reply);
        Post post = postRepository.save(postFormAdapter.updatePostFromReply(reply));

        addMessage(author, reply, renderedContent, post);
        return reply;
    }

    // 消息
    private void addMessage(User author, Reply reply, RenderedContent renderedContent, Post post) {
        List<User> atedUsers = renderedContent.getAtedUsers();
        List<Message> messages = Lists.newArrayListWithExpectedSize(atedUsers.size());
        Date now = new Date();
        for (User u : atedUsers) {
            Message message = new Message();
            message.setAuthorId(author.getId());
            message.setTargetUserId(u.getId());
            message.setCreatedTime(now);
            message.setPostId(post.getId());
            message.setReplyId(reply.getId());
            message.setContent(author.getNick() + "在回复" + post.getTitle().substring(0, 10) + "的回复 #floor" + reply.getFloor() + " ＠了你");
            message.setType(MessageType.at);
            messages.add(message);
        }
        messageService.addMessages(messages);
    }

    private Reply createReply(ReplyForm replyForm, User author) {
        Reply reply = new Reply();
        reply.setCreatedTime(new Date());
        reply.setUpdatedTime(reply.getCreatedTime());
        reply.setAuthor(author);
        reply.setStatus(replyForm.getStatus());

        // Post不走缓存
        Post post = postRepository.findOne(replyForm.getPostId());
        reply.setPost(post);
        reply.setFloor(post.getFloorCount() + 1);

        Long replyId = replyForm.getReplyId();
        if (replyId != null) {
            reply.setReply(this.findOne(replyId));
        }
        reply.setContent(replyForm.getContent());
        return reply;
    }


    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostFormAdapter postFormAdapter;

    @Autowired
    private ContentRenderer renderer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

}
