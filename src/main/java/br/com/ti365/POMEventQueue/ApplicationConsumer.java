package br.com.ti365.POMEventQueue;

import br.com.ti365.POMEventQueue.consumer.ConsumirdorEvento;


public class ApplicationConsumer {

	public static void main(String[] args) {

		ApplicationConsumer applicationConsumer = new ApplicationConsumer();
		applicationConsumer.iniciar();
		
	}
	
	private void iniciar() {
		ConsumirdorEvento consumirdorEvento = new ConsumirdorEvento();
		consumirdorEvento.executar();
	}
	

}
