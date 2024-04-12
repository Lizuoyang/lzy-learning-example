package com.lzy.example.mqtt.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * mqtt 具体业务实现，操作类
 * 订阅主题、创建连接、去掉订阅、发送消息等操作
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Slf4j
public class MyMQTTClient {
    private static MqttClient client;
    private String host;
    private String username;
    private String password;
    private String clientId;
    private int timeout;
    private int keepalive;
    public MyMQTTClient(String host, String username, String password, String clientId, int timeOut, int keepAlive) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.timeout = timeOut;
        this.keepalive = keepAlive;
    }

    public static MqttClient getClient() {
        return client;
    }
    public static void setClient(MqttClient client) {
        MyMQTTClient.client = client;
    }
    /**
     * 设置mqtt连接参数
     *
     */
    public MqttConnectOptions setMqttConnectOptions(String username, String password, int timeout, int keepalive) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        return options;
    }
    /**
     * 连接mqtt服务端，得到MqttClient连接对象
     */
    public void connect() throws MqttException {
        if (client == null) {
            client = new MqttClient(host, clientId, new MemoryPersistence());
            client.setCallback(new MyMQTTCallback(MyMQTTClient.this));
        }
        MqttConnectOptions mqttConnectOptions = setMqttConnectOptions(username, password, timeout, keepalive);
        if (!client.isConnected()) {
            client.connect(mqttConnectOptions);
        } else {
            client.disconnect();
            client.connect(mqttConnectOptions);
        }
        //未发生异常，则连接成功
        log.info("MQTT connect success");
    }
    /**
     * 发布，默认qos为0，非持久化
     *
     */
    public void publish(String pushMessage, String topic,int qos) {
        publish(pushMessage, topic, qos, false);
    }
    /**
     * 发布消息
     *
     * @param pushMessage
     * @param topic
     * @param qos
     * @param retained:留存
     */
    public void publish(String pushMessage, String topic, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setPayload(pushMessage.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        MqttTopic mqttTopic = MyMQTTClient.getClient().getTopic(topic);
        if (null == mqttTopic) {
            log.error("主题没有找到");
        }
        //Delivery:配送
        MqttDeliveryToken token;
        //注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
        synchronized (this) {
            try {
                //也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
                token = mqttTopic.publish(message);
                token.waitForCompletion(1000L);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            MyMQTTClient.getClient().subscribe(topic, qos);
            log.info("订阅主题"+topic+"成功！");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    /**
     * 取消订阅主题
     *
     * @param topic 主题名称
     */
    public void cleanTopic(String topic) {
        if (client != null && client.isConnected()) {
            try {
                client.unsubscribe(topic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            log.info("取消订阅主题失败！");
        }
    }
}
