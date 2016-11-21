package me.micro.bbs.message.support;

import me.micro.bbs.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
