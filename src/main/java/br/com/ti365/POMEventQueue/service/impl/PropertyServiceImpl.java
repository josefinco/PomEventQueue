package br.com.ti365.POMEventQueue.service.impl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ti365.POMEventQueue.service.PropertyService;


public class PropertyServiceImpl implements PropertyService {
	private static Logger log = LogManager.getLogger(PropertyServiceImpl.class);


	/**
	 * Método responsável por realizar a leitura do arquivo properties
	 * 
	 * @return Arquivo properties carregado
	 */
	public Properties loadProperties() {
		Properties properties = new Properties();

		// Para testes locais, utilizar a String abaixo
		 String propFileName = "application.properties";
//		String propFileName = "./resources/application.properties";
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {

			log.debug("Buscando arquivo " + propFileName + "...");

			if (inputStream != null) {
				properties.load(inputStream);
				log.debug("Arquivo carregando com sucesso");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return properties;
	}

}
