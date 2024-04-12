package com.lzy.example.mqtt.service;

import com.lzy.example.mqtt.domain.request.CreatePkRequest;

/**
 * 房间pk 服务层
 * @author Lizuoyang
 * @date 2024/04/12
 */
public interface RoomPkService {
    void createPk(CreatePkRequest request);
}
