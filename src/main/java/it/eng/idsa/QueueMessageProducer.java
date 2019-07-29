package it.eng.idsa;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.idsa.service.util.DemoDataUtils;



/**
 * Message producer which sends the message to ActiveMQ Broker
 */
public class QueueMessageProducer {
	private static final String PRODUCER_SENT_MESSAGE = "Producer sent text message:";

	private static Logger logger = Logger.getLogger(QueueMessageProducer.class.getName());

    private String activeMqBrokerUri;
    private String username;
    private String password;
    private static boolean stop;

    public QueueMessageProducer(String activeMqBrokerUri, String username, String password) {
        super();
        this.activeMqBrokerUri = activeMqBrokerUri;
        this.username = username;
        this.password = password;
        QueueMessageProducer.stop=false;
    }

    public void sendDummyMessages(String queueName) {
        logger.debug("QueueMessageProducer started " + this.activeMqBrokerUri);
        ConnectionFactory connFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer msgProducer = null;
        try {
            connFactory = new ActiveMQConnectionFactory(username, password, activeMqBrokerUri);
            connection = connFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            msgProducer = session.createProducer(session.createQueue(queueName));

            for (int i = 1; i <= DemoDataUtils.NUMBER_OF_MESSAGES; i++) {
                TextMessage textMessage = session.createTextMessage(DemoDataUtils.buildDummyMessage(i));
                msgProducer.send(textMessage);
                logger.debug(PRODUCER_SENT_MESSAGE + textMessage.getText());
                try {
                    Thread.sleep(10000); // sleep for 10 seconds
                } catch (InterruptedException e) {
                }
            }
            logger.debug("QueueMessageProducer completed");
        } catch (JMSException e) {
        	logger.debug("Caught exception: " + e.getMessage());
        }
        try {
            if (msgProducer != null) {
                msgProducer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ignore) {
        }
    }
    
    
    
    public void destroyQueue(String queueName) {
        QueueMessageProducer.stop=true;
        /*
    	ActiveMQConnection conn = null;
         try {
             conn = (ActiveMQConnection) new    ActiveMQConnectionFactory(username, password, this.activeMqBrokerUri).createConnection();
             String [] destinations=new ActiveMQQueue(queueName).getDestinationPaths();
             logger.debug("destinations[0]="+destinations[0]);
             Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
             ActiveMQQueue queue = (ActiveMQQueue) session.createQueue(queueName);
             logger.debug("test empty "+ isQueueEmpty(queueName));

             try {
                 //HERE is where the operation is failing
                 conn.destroyDestination(queue);
             } 
             finally {
                 session.close();
                 conn.close();
             }
             conn.destroyDestination(new ActiveMQQueue(queueName));
             logger.debug("QueueMessageProducer deleted " + this.activeMqBrokerUri);
         }catch(Exception e) {
        	 e.printStackTrace();
         }*/
    }
    
    
	/*
	 * private boolean isQueueEmpty(String queueName) throws JMSException{
	 * ActiveMQConnectionFactory connFactory = new
	 * ActiveMQConnectionFactory(username, password, activeMqBrokerUri);
	 * ActiveMQConnection connection = (ActiveMQConnection)
	 * connFactory.createConnection(); connection.start(); Session session =
	 * connection.createSession(false, Session.AUTO_ACKNOWLEDGE); ActiveMQQueue
	 * queue = (ActiveMQQueue) session.createQueue(queueName); QueueBrowser
	 * queueBrowser = session.createBrowser(queue); try { if
	 * (queueBrowser.getEnumeration().hasMoreElements()){ queueBrowser.close();
	 * return false; } return true; } finally { if (queueBrowser != null) {
	 * queueBrowser.close(); } session.close(); connection.close(); } }
	 */

    public void sendDummyMessagesContinuosly(String queueName) {
    	logger.debug("QueueMessageProducer started " + this.activeMqBrokerUri);
        ConnectionFactory connFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer msgProducer = null;
        ArtifactResponseMessage artifactResponseMessage=new ArtifactResponseMessage();
        ObjectMapper mapper=new ObjectMapper();
        String artifact=null;
        try {
			artifact=mapper.writeValueAsString(artifactResponseMessage);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            connFactory = new ActiveMQConnectionFactory(username, password, activeMqBrokerUri);
            connection = connFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            msgProducer = session.createProducer(session.createQueue(queueName));
            int i=1;
            while(!stop) {	
                TextMessage textMessage = session.createTextMessage(artifact+" *** "+DemoDataUtils.buildDummyMessage(i));
                msgProducer.send(textMessage);
                logger.debug(PRODUCER_SENT_MESSAGE + textMessage.getText());
                try {
                    Thread.sleep(10000); // sleep for 10 seconds
                } catch (InterruptedException e) {
                }
                i++;
            }
            //System.out.println("QueueMessageProducer completed");
        } catch (JMSException e) {
        	logger.debug("Caught exception: " + e.getMessage());
        }
        try {
            if (msgProducer != null) {
                msgProducer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ignore) {
        }
    }
    
    
}
