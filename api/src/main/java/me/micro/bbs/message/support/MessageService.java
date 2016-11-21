package me.micro.bbs.message.support;

import me.micro.bbs.enums.MessageType;
import me.micro.bbs.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Message> findMine(Long userId) {
        return messageRepository.findByTargetUserId(userId);
    }

    public void addMessages(List<Message> messages) {
        messageRepository.save(messages);
    }

    public Message createMessage(Long replyId, Long authorId, Long postId, Date now, Long targetId, String content, MessageType messageType) {
        Message message = new Message();
        message.setAuthorId(authorId);
        message.setTargetUserId(targetId);
        message.setCreatedTime(now);
        message.setPostId(postId);
        message.setReplyId(replyId);
        message.setContent(content);
        message.setType(messageType);
        return message;
    }

}
