package com.lzy.example.mqtt.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * 回调类（执行完操作之后进行回调）
 * @author Lizuoyang
 * @date 2024/04/12
 */
@Slf4j
public class MyMQTTCallback implements MqttCallbackExtended {
    private MyMQTTClient myMQTTClient;
    public MyMQTTCallback(MyMQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }

    /**
     * 丢失连接，可在这里做重连
     * 只会调用一次
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 1;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）  看你们自己选择
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes+=1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
                MyMQTTClient.getClient().reconnect();
            } catch (MqttException e) {
                log.error("mqtt断连异常", e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
            }
        }
    }
    /**
     * @param topic
     * @param mqttMessage
     * @throws Exception
     * subscribe后得到的消息会执行到这里面
     * 订阅者收到消息之后执行
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        Resource app = new ClassPathResource("application.yml");
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(app);
        Properties properties = yamlPropertiesFactoryBean.getObject();
        if (properties.getProperty("spring.profiles.active").equals("serve")) {
            System.out.println("服务端发完消息之后调用");
        } else {
            System.out.println("客户端接收到消息之后调用");
        }
        log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));
    }
    /**
     *连接成功后的回调 可以在这个方法执行 订阅主题  生成Bean的 MqttConfiguration方法中订阅主题 出现bug
     *重新连接后  主题也需要再次订阅  将重新订阅主题放在连接成功后的回调 比较合理
     * @param reconnect
     * @param serverURI
     */
    @Override
    public  void  connectComplete(boolean reconnect,String serverURI){
        log.info("MQTT 连接成功，连接方式：{}",reconnect?"重连":"直连");
        //订阅主题(可以在这里订阅主题)
    }
    /**
     * * 消息到达后
     * subscribe后，执行的回调函数
     * publish后，配送完成后回调的方法
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用");
        log.info("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }
}
