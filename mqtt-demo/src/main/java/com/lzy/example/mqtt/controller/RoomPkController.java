package com.lzy.example.mqtt.controller;

import com.lzy.example.mqtt.domain.ResponseResult;
import com.lzy.example.mqtt.domain.request.CreatePkRequest;
import com.lzy.example.mqtt.domain.request.SendGiftRequest;
import com.lzy.example.mqtt.service.RoomPkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 房间pk前端控制器
 * @author Lizuoyang
 * @date 2024/04/12
 */
@RestController
@RequestMapping("/room/pk")
public class RoomPkController {
    @Autowired
    private RoomPkService roomPkService;

    @PostMapping("/start")
    public ResponseResult startPk(@RequestBody CreatePkRequest params) {
        roomPkService.createPk(params);
        return ResponseResult.success();
    }

    @PostMapping("/send")
    public ResponseResult sendGift(@RequestBody SendGiftRequest params) {
        roomPkService.sendMsg(params);
        return ResponseResult.success();
    }
}
