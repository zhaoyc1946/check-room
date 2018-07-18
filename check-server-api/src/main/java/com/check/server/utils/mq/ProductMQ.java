package com.check.server.utils.mq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.check.commom.mq.MQTopic;
import com.check.commom.utils.JsonUtils;
import com.check.server.config.RocketMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description: MQ生产者
 * @author: Mr.ZHAO
 * @cereate: 2018/07/13 00:06:46
 */
public class ProductMQ {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMQ.class);

    /**
     * 发送消息队列
     *
     * @param topic   话题
     * @param message 消息内容
     * @return
     */
    public static ResultDto sendMessage(MQTopic topic, String message) {
        if (message == null) {
            return new ResultDto(false, "message can't be empty");
        }
        Message msg = new Message(topic.toString(), null, null, message.getBytes());
        return sendMessage(msg);
    }

    /**
     * 发送延时消息队列
     *
     * @param topic      话题
     * @param message    消息内容
     * @param delayLevel 延时级别
     * @return
     */
    public static ResultDto sendDelayMessage(MQTopic topic, String message, int delayLevel) {
        if (message == null) {
            return new ResultDto(false, "message can't be empty");
        }

        Message msg = new Message(topic.toString(), null, null, message.getBytes());
        msg.setDelayTimeLevel(delayLevel);

        return sendMessage(msg);
    }

    /**
     * 发送消息队列
     *
     * @param topic   话题
     * @param tag     标签
     * @param keys    关键词
     * @param message 消息内容
     * @return
     */
    public static ResultDto sendMessage(MQTopic topic, String tag, String keys, String message) {
        if (message == null) {
            return new ResultDto(false, "message can't be empty");
        }
        Message msg = new Message(topic.toString(), tag, keys, message.getBytes());
        return sendMessage(msg);
    }

    /**
     * 发送消息队列
     *
     * @param msg 消息
     * @return
     */
    public static ResultDto sendMessage(Message msg) {
        SendResult sendResult = null;
        ResultDto resultDto = new ResultDto();
        if (msg == null) {
            resultDto.setResult(false);
            resultDto.setErrMsg("消息为空，请检查参数！");
            return resultDto;
        }
        try {
            sendResult = RocketMQConfig.init().send(msg);
        } catch (MQClientException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现MQClientException异常：" + e.getMessage());
            LOGGER.error("发送异常：" + e.getMessage() + JsonUtils.objectToJson(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            LOGGER.error("异常堆栈信息：" + exceptionMessage);
        } catch (RemotingException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现RemotingException异常：" + e.getMessage());
            LOGGER.error("发送异常：" + e.getMessage() + JsonUtils.objectToJson(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            LOGGER.error("异常堆栈信息：" + exceptionMessage);
        } catch (MQBrokerException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现MQBrokerException异常：" + e.getMessage());
            LOGGER.error("发送异常：" + e.getMessage() + JsonUtils.objectToJson(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            LOGGER.error("异常堆栈信息：" + exceptionMessage);
        } catch (InterruptedException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现InterruptedException异常：" + e.getMessage());
            LOGGER.error("发送异常：" + e.getMessage() + JsonUtils.objectToJson(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            LOGGER.error("异常堆栈信息：" + exceptionMessage);
        }
        LOGGER.info("sendResult: {}", JsonUtils.objectToJson(sendResult));
        if (sendResult == null) {
            resultDto.setResult(false);
            LOGGER.info("发送失败：" + JsonUtils.objectToJson(msg));
            return resultDto;
        }
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            resultDto.setResult(true);
            LOGGER.info("发送成功：" + JsonUtils.objectToJson(msg));
        }
        if (sendResult.getSendStatus().equals(SendStatus.FLUSH_DISK_TIMEOUT)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("发送成功，到那时服务器未刷盘！");
            LOGGER.info("消息収送成功，但是服务器刷盘超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JsonUtils.objectToJson(msg));
        }
        //暂时MQ没有集群，所以不会出现这种返回结果
        if (sendResult.getSendStatus().equals(SendStatus.FLUSH_SLAVE_TIMEOUT)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("发送成功，到那时服务器未同步slave！");
            LOGGER.info("消息収送成功，但是服务器同步到 Slave 时超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JsonUtils.objectToJson(msg));
        }
        if (sendResult.getSendStatus().equals(SendStatus.SLAVE_NOT_AVAILABLE)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("消息发送成功，但是slave不可用");
            LOGGER.info("消息収送成功，但是此时 slave 不可用，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JsonUtils.objectToJson(msg));
        }
        return resultDto;
    }
}
