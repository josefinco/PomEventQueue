package br.com.ti365.POMEventQueue.consumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class ConsumirdorEvento {

	private final KafkaConsumer<String, String> consumer;

	public ConsumirdorEvento() {
		consumer = criarConsumer();
	}

	private KafkaConsumer<String, String> criarConsumer() {
		if (consumer != null)
			return consumer;

		Properties properties = new Properties();
		properties.put("bootstrap.servers", "172.31.254.21:9093");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("group.id", "999");
		properties.put("enable.auto.commit", "false");
		properties.put("security.protocol","SSL");
		properties.put("metadata.max.age.ms", "30000");
		properties.put("ssl.truststore.location","conf/pomTrustStore");
		properties.put("ssl.truststore.password","changeit");
		properties.put("ssl.keystore.location","conf/pomKeyStore");
		properties.put("ssl.keystore.password","changeit");
		properties.put("ssl.enabled.protocols","TLSv1.2");

		return new KafkaConsumer<String, String>(properties);
	}

	public void executar() {
		List<String> topicos = new ArrayList<>();
		topicos= Arrays.asList("POM.Default.ENRICHEDATTEMPTRESULT");
		
		
		try {
			System.out.println("Iniciando consumer!");
			consumer.subscribe(topicos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		boolean continua = true;
		while (continua) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				gravarMensagem(record.value(), record.topic(), record.partition(), record.offset());
				consumer.commitAsync();
			}

		}
	}

	public void gravarMensagem(String messages, String topic, int particao, long offset) {
		System.out.println("*******************************************************************************");
		System.out.println("Offset:" + offset  + " Mensagem recebida: " + messages + " topic: " + topic + " Particao: " + particao);
		System.out.println("*******************************************************************************");
	}
}
