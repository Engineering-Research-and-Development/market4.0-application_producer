package it.eng.idsa.service.util;

import org.apache.log4j.Logger;

import de.fraunhofer.iais.eis.Message;
import it.eng.idsa.MessageProducerApp;


/**
* The ProducingThread class is responsible for activating the queue
*
* @author  Gabriele De Luca, Milan Karajovic
*/
public class ProducingThread extends Thread {
	private static Logger logger = Logger.getLogger(ProducingThread.class);
	private Message message;
	
	public ProducingThread(Message message) {
		this.message=message;
	}
	
	public void run() {
		try {
			MessageProducerApp messageProducerApp=new MessageProducerApp();
			messageProducerApp.activateProducing(message);
		} catch(Exception v) {
			logger.debug(v);
		}
	}  
}
