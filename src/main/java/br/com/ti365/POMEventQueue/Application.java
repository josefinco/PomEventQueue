package br.com.ti365.POMEventQueue;

import com.rabbitmq.client.Channel;

import br.com.ti365.POMEventQueue.service.ConnectionService;
import br.com.ti365.POMEventQueue.service.impl.ConnectionServiceImpl;


public class Application {

	public static void main(String[] args) {
		ConnectionService connectionService = new ConnectionServiceImpl();
		
		System.out.println("Hello World!");

		Channel channel = connectionService.connectionFactory();
		connectionService.publish(channel, "OLÁ!");
		
	}

}
