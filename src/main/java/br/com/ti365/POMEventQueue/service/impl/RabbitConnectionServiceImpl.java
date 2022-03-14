package br.com.ti365.POMEventQueue.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.ti365.POMEventQueue.service.RabbitConnectionService;

public class RabbitConnectionServiceImpl implements RabbitConnectionService {

	
	private static Logger log = LogManager.getLogger(RabbitConnectionServiceImpl.class);
	private PropertyServiceImpl propertyService = new PropertyServiceImpl();
	Properties properties = propertyService.loadProperties();
	String queue = properties.getProperty("rabbitmq.queue");
	
	public Channel connectionFactory() {
		try {
			log.info("Criando o objeto ConnectionFactory");
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(properties.getProperty("rabbitmq.host"));
			factory.setUsername(properties.getProperty("rabbitmq.username"));
			factory.setPassword(properties.getProperty("rabbitmq.password"));
			factory.setPort(Integer.parseInt(properties.getProperty("rabbitmq.port")));
			factory.setVirtualHost(properties.getProperty("rabbitmq.virtualhost"));
//			factory.useSslProtocol();
			
			log.info("Conexão estabelecida com sucesso");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(queue, true, false, false, null);
			System.out.println(" [*] Conexões abertas para recebimento de mensagens.");
			return channel;
		} catch (Exception e) {
			log.error("Falha ao criar o objeto ConnectionFactory", e);
		}
		return null;
	}

	public void publish(Channel channel, String message) {

		try {
			channel.basicPublish("", queue, null, message.getBytes());
			System.out.println(" Mensagem publicada: " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
