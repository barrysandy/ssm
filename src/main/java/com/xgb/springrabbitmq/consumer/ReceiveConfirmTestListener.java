package com.xgb.springrabbitmq.consumer;

import com.xiaoshu.tools.ToolsHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.xgb.springrabbitmq.dto.DtoMessage;

import java.io.IOException;


@Service("receiveConfirmTestListener")
public class ReceiveConfirmTestListener implements ChannelAwareMessageListener {  
	private static Logger logger = LoggerFactory.getLogger(ReceiveConfirmTestListener.class);

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try{
			String flag = "fail";
			String json = new String(message.getBody());
			DtoMessage messageJson = DtoMessage.transformationToJson(json);
			System.out.println("Consumer Message 消费消息 -- messageJson.getUrl(): " + messageJson.getUrl() );
			if(messageJson != null) {
				if(messageJson.getRequestMethod() != null) {
					if("get".equals(messageJson.getRequestMethod())) {
						flag = ToolsHttpRequest.sendGet(messageJson.getUrl(), messageJson.getParams());
					}else if("post".equals(messageJson.getRequestMethod())) {
						flag = ToolsHttpRequest.sendPost(messageJson.getUrl(), messageJson.getParams());
					}
				}
			}
			
			if("ok".equals(flag)) {
				System.out.println("Ack message ："  + messageJson.getUrl());
				basicACK(message, channel);//处理正常 ACK消息
			} else if("retry".equals(flag)){
				System.out.println("Retry message ："  + messageJson.getUrl());
				basicRetry(message, channel);//处理重试 ACK消息
			} else if("fail".equals(flag)){
				System.out.println("NAck message ："  + messageJson.getUrl());
				basicNACK(message, channel);//处理异常 NACK消息
			}
		}catch(Exception e){
			e.printStackTrace();
			//TODO 业务处理
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
		}
	}

	//正常消费掉后通知mq服务器移除此条mq
	private void basicACK(Message message,Channel channel){
		try{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
			System.out.println("BasicAck message ："  + message.getBody() );
		}catch(IOException e){
			logger.error("通知服务器移除mq时异常，异常信息：" + e);
		}
	}
	//处理异常，mq不再回归队列
	private void basicNACK(Message message,Channel channel){
		try{
			//TODO 需要重新编写处理 移除消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
			System.out.println("BasicNack message ："  + message.getBody() );
		}catch(IOException e){
			logger.error("mq重新进入服务器时出现异常，异常信息：" + e);
		}
	}

	//处理重试，mq重回队列
	private void basicRetry(Message message,Channel channel){
		try{
			//TODO 需要重新编写处理 移除消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
			System.out.println("BasicRetry message ："  + message.getBody() );
		}catch(IOException e){
			logger.error("mq重试进入服务器时出现异常，异常信息：" + e);
		}
	}
  
}  
