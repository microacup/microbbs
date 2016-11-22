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
import me.micro.bbs.user.support.ProfileService;
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
        Reply reply = createReply(replyForm, username);

        // 渲染markdown
        RenderedContent renderedContent = renderer.render(replyForm.getContent());
        String renderedHtml = renderedContent.getHtml();
        reply.setRenderedContent(renderedHtml);
        replyRepository.save(reply);
        postRepository.save(postFormAdapter.updatePostFromReply(reply));
        addMessage(reply, renderedContent.getAtedUsers());

        // 更新Profile
        profileService.addReplyCount(reply.getAuthor().getId());
        return reply;
    }

    // 消息
    private void addMessage(Reply reply, List<User> atedUsers) {
        User author = reply.getAuthor();
        Long authorId = author.getId();
        Post post = reply.getPost();
        Long postId = post.getId();
        Date now = new Date();
        Long replyId = reply.getId();

        // 相同的targetId只被通知一次，所以实际的比期望的小
        List<Message> messages = Lists.newArrayListWithExpectedSize(atedUsers.size() + 2);

        // 帖子的回复
        Long target1 = post.getAuthor().getId();
        String content1 = author.getNick() + "回复了你的帖子：" + post.getTitle() + " #floor" + reply.getFloor();
        Message postmsg = messageService.createMessage(reply, authorId, postId, now, target1, content1, MessageType.reply);
        messages.add(postmsg);

        // 回复的消息
        Reply reply2 = reply.getReply();
        Long target2 = Long.MIN_VALUE;
        if (reply2 !=  null) {
            target2 = reply2.getAuthor().getId();
            if (!target1.equals(target2)) {
                String content2 = author.getNick() + "在话题：" + post.getTitle() + " #floor" + reply.getFloor() + " 回复了你";
                Message replymsg = messageService.createMessage(reply, authorId, postId, now, target2, content2, MessageType.reply2);
                messages.clear(); // reply2优先
                messages.add(replymsg);
            }
        }

        // at的消息
        for (User u : atedUsers) {
            Long targetId = u.getId();
            if (target1.equals(targetId) || target2.equals(targetId)) {
                continue;
            }

            String content = author.getNick() + "在话题：" + post.getTitle() + "的回复 #floor" + reply.getFloor() + " ＠了你";
            Message message = messageService.createMessage(reply, authorId, postId, now, targetId, content, MessageType.at);
            messages.add(message);
        }
        messageService.addMessages(messages);
    }

    private Reply createReply(ReplyForm replyForm, String username) {
        User author = userRepository.findByUsername(username);

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

    @Autowired
    private ProfileService profileService;

}
