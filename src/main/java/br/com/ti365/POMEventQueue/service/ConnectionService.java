package br.com.ti365.POMEventQueue.service;

import com.rabbitmq.client.Channel;

public interface ConnectionService {

	Channel connectionFactory();
	
	void publish(Channel channel, String message);
}
