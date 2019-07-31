


import it.eng.idsa.QueueMessageProducer;



public class ActiveMQBrokerProducerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String brokerUrl = "tcp://192.168.56.102:61616";
		//brokerUrl="ssl://192.168.56.103:61713";

		QueueMessageProducer queProducer = new QueueMessageProducer(brokerUrl, "admin", "admin");
        queProducer.sendDummyMessages("milan");

	}

}
