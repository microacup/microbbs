package me.micro.bbs.api;

import me.micro.bbs.client.Result;
import me.micro.bbs.consts.Uris;
import me.micro.bbs.message.Message;
import me.micro.bbs.message.support.MessageService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // 我的消息列表
    @GetMapping(Uris.API_MESSAGES)
    public Result<List<Message>> findUnReadMessages() {
        List<Message> mine = messageService.findMine();
        return Result.ok(mine);
    }

    // 标记为已读
    @PutMapping(Uris.API_MESSAGES)
    public Result<String> read(@RequestParam("ids") String ids) {
        String[] idArray = ids.split(",");
        Long[] idsLong = new Long[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
            idsLong[i] = Long.parseLong(idArray[i]);
        }

        int success = messageService.readed(idsLong);
        if (success == 1) return Result.error(HttpStatus.SC_NOT_ACCEPTABLE, "非法请求");
        return Result.ok("标记已读成功");
    }


}
