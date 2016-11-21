package me.micro.bbs.api;

import me.micro.bbs.client.Result;
import me.micro.bbs.consts.Uris;
import me.micro.bbs.message.Message;
import me.micro.bbs.message.support.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * 消息API
 *
 * Created by microacup on 2016/11/21.
 */
@RestController
public class MessageApi {
    @Autowired
    private MessageService messageService;

    @GetMapping
    @RequestMapping(Uris.API_MESSAGES)
    public Result<List<Message>> findUnReadMessages(Principal principal) {
        List<Message> mine = messageService.findMine();
        return Result.ok(mine);
    }


}
