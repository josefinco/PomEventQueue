package br.com.ti365.POMEventQueue;

import java.util.Properties;

import com.avaya.pim.eventsdk.core.EventTypeEnum;
import com.avaya.pim.eventsdk.core.POMEventRegistrar;
import com.avaya.pim.eventsdk.exception.POMEventException;
import com.avaya.pim.eventsdk.logger.ILogger;

import br.com.ti365.POMEventQueue.logger.LoggerImpl;
import br.com.ti365.POMEventQueue.service.impl.PomEventServiceImpl;
import br.com.ti365.POMEventQueue.service.impl.PropertyServiceImpl;


public class Application {

	public static void main(String[] args) {
		ILogger logger = LoggerImpl.getTracer();
		
		PropertyServiceImpl propertyService = new PropertyServiceImpl();
		Properties properties = propertyService.loadProperties();
		String pomuser = properties.getProperty("pom.user");
		String pompassword = properties.getProperty("pom.password");
		String pomhost = properties.getProperty("pom.host");

		

		POMEventRegistrar pomEventRegistrar = POMEventRegistrar.getInstance();
		try {
			pomEventRegistrar.login(pomuser, pompassword, pomhost, logger, null /* LoginOptions */);
			PomEventServiceImpl enrichedAttemptResultCallback = new PomEventServiceImpl();
			pomEventRegistrar.subscribe(EventTypeEnum.ENRICHED_ATTEMPT_RESULT, enrichedAttemptResultCallback);
			pomEventRegistrar.initialize();
		} catch (POMEventException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
