package me.micro.bbs.message.support;

import me.micro.bbs.enums.MessageType;
import me.micro.bbs.message.Message;
import me.micro.bbs.reply.Reply;
import me.micro.bbs.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * MessageService
 *
 * Created by microacup on 2016/11/21.
 */
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public int readed(Long messageId) {
        return messageRepository.readed(messageId);
    }

    public List<Message> findMine() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedUserException("未登录的请求");
        }

        User user = (User) authentication.getPrincipal();
        return messageRepository.findByTargetUserIdAndHasReadFalseOrderByCreatedTimeDesc(user.getId());
    }

    public void addMessages(List<Message> messages) {
        messageRepository.save(messages);
    }

    public Message createMessage(Reply reply, Long authorId, Long postId, Date now, Long targetId, String content, MessageType messageType) {
        Message message = new Message();
        message.setAuthorId(authorId);
        message.setTargetUserId(targetId);
        message.setCreatedTime(now);
        message.setPostId(postId);
        message.setReplyId(reply.getId());
        message.setContent(content);
        message.setType(messageType);
        message.setFloor(reply.getFloor());
        return message;
    }

}
