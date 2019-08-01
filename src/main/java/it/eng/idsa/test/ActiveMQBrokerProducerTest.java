package it.eng.idsa.test;



import de.fraunhofer.iais.eis.MessageBuilder;
import it.eng.idsa.QueueMessageProducer;


/**
* The ActiveMQBrokerProducerTest class creates an ActiveMQ queue for testing
*
* @author  Gabriele De Luca, Milan Karajovic
*/
public class ActiveMQBrokerProducerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String brokerUrl = "tcp://192.168.56.102:61616";
		//brokerUrl="ssl://192.168.56.103:61713";

		QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
		queProducer.sendDummyMessages("milan", new MessageBuilder().build());

	}

}
