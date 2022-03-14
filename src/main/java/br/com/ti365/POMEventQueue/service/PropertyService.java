package br.com.ti365.POMEventQueue.service;

import java.util.Properties;

public interface PropertyService {
	
	/**
	 * Método responsável por realizar a leitura do arquivo properties
	 * 
	 * @return Arquivo properties carregado
	 */
	 Properties loadProperties();
	
}
