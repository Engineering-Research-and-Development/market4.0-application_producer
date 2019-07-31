package it.eng.idsa;

import org.apache.log4j.Logger;

import it.eng.idsa.service.util.DemoDataUtils;

public class MessageProducerApp {
	private static Logger logger = Logger.getLogger(MessageProducerApp.class.getName());

    public static void main(String[] args) {
        String brokerUrl = DemoDataUtils.readBrokerURL();
        QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.sendDummyMessages(DemoDataUtils.DESTINATION);

    }
    
    public void activateProducing() {
    	logger.debug("activateProducing");
    	String brokerUrl = DemoDataUtils.readBrokerURL();
        QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.sendDummyMessages(DemoDataUtils.DESTINATION);
    }
    
    public void deactivateProducing() {
    	logger.debug("deactivateProducing");
    	String brokerUrl = DemoDataUtils.readBrokerURL();
        QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.destroyQueue(DemoDataUtils.DESTINATION);
    }
}

