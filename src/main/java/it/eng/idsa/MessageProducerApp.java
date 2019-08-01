package it.eng.idsa;

import org.apache.log4j.Logger;

import de.fraunhofer.iais.eis.Message;
import it.eng.idsa.service.util.DemoDataUtils;


/**
* The MessageProducerApp class is responsible to activate/deactivate ActiveMQ queues
*
* @author  Gabriele De Luca, Milan Karajovic
*/
public class MessageProducerApp {
	private static Logger logger = Logger.getLogger(MessageProducerApp.class.getName());

	/*
	 * public static void main(String[] args) { String brokerUrl =
	 * DemoDataUtils.readBrokerURL(); QueueMessageProducer queProducer = new
	 * QueueMessageProducer(brokerUrl, "admin", "admin");
	 * queProducer.sendDummyMessages(DemoDataUtils.DESTINATION);
	 * 
	 * }
	 */
    
    public void activateProducing(Message message) {
    	logger.debug("activateProducing");
    	String brokerUrl = DemoDataUtils.readBrokerURL();
        QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.sendDummyMessages(DemoDataUtils.DESTINATION, message);
    }
    
    public void deactivateProducing() {
    	logger.debug("deactivateProducing");
    	String brokerUrl = DemoDataUtils.readBrokerURL();
        QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.destroyQueue(DemoDataUtils.DESTINATION);
    }
}

