package br.com.ti365.POMEventQueue.service.impl;

import com.avaya.pim.eventsdk.core.Event;
import com.avaya.pim.eventsdk.core.EventTypeEnum;
import com.avaya.pim.eventsdk.core.POMEnrichedAttemptEventNotifier;
import com.avaya.pim.eventsdk.dto.attempt.EnrichedAttemptEvent;
import com.rabbitmq.client.Channel;

import br.com.ti365.POMEventQueue.service.RabbitConnectionService;

public class PomEventServiceImpl implements POMEnrichedAttemptEventNotifier{

	
	RabbitConnectionService connectionService = new RabbitConnectionServiceImpl();
	Channel channel = connectionService.connectionFactory();
	
	@Override
	public void onEvent(Event event) {
		if ((event instanceof EnrichedAttemptEvent)
				&& (event.getEventTypeEnum() == EventTypeEnum.ENRICHED_ATTEMPT_RESULT)) {
			System.out.println("Evento recebido: " + event);
			connectionService.publish(channel, event.toString());
		}
		
	}

}
