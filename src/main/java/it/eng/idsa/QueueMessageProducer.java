package it.eng.idsa;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.iais.eis.ArtifactResponseMessageBuilder;
import de.fraunhofer.iais.eis.Message;
import de.fraunhofer.iais.eis.MessageBuilder;
import de.fraunhofer.iais.eis.ids.jsonld.Serializer;
import it.eng.idsa.model.fiware.device.Device;
import it.eng.idsa.service.util.PropertiesConfig;




/**
 * The QueueMessageProducer sends the message to ActiveMQ Broker
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class QueueMessageProducer {
	private static final String PRODUCER_SENT_MESSAGE = "Producer sent text message:";
	private static final PropertiesConfig CONFIG_PROPERTIES = PropertiesConfig.getInstance();

	private static Logger logger = Logger.getLogger(QueueMessageProducer.class.getName());

	private String activeMqBrokerUri;
	private String username;
	private String password;
	private static URI connectorURI;

	public QueueMessageProducer(String activeMqBrokerUri, String username, String password) {
		super();
		this.activeMqBrokerUri = activeMqBrokerUri;
		this.username = username;
		this.password = password;
		try {
			connectorURI=new URI(CONFIG_PROPERTIES.getProperty("uriSchema")+CONFIG_PROPERTIES.getProperty("uriAuthority")+CONFIG_PROPERTIES.getProperty("uriConnector")+UUID.randomUUID().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void destroyQueue(String queueName) {

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

	public void sendDummyMessages(String queueName, Message requestMessage) {
		logger.debug("QueueMessageProducer started " + this.activeMqBrokerUri);
		ConnectionFactory connFactory = null;
		Connection connection = null;
		Session session = null;
		MessageProducer msgProducer = null;
		ObjectMapper mapper=new ObjectMapper();


		try {
			connFactory = new ActiveMQConnectionFactory(username, password, activeMqBrokerUri);
			connection = connFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			msgProducer = session.createProducer(session.createQueue(queueName));

			GregorianCalendar gcal = new GregorianCalendar();
			XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gcal);

			Message message=new ArtifactResponseMessageBuilder(new URI (CONFIG_PROPERTIES.getProperty("uriSchema")+CONFIG_PROPERTIES.getProperty("uriAuthority")+CONFIG_PROPERTIES.getProperty("uriPath")+UUID.randomUUID().toString()))
					._issuerConnector_(connectorURI)
					._issued_(xgcal)
					._modelVersion_("1.0.3")
					._correlationMessage_(requestMessage.getId())
					.build();

			Device device=new Device();
			String deviceString=mapper.writeValueAsString(device);

			String msgSerialized = new Serializer().serializePlainJson(message);
			HttpEntity entity=MultipartEntityBuilder
					.create()
					.addTextBody("header", msgSerialized, org.apache.http.entity.ContentType.APPLICATION_JSON)
					.addTextBody("payload", deviceString, org.apache.http.entity.ContentType.APPLICATION_JSON)
					.build();



			/*
				MimeMultipart multipart=new MimeMultipart();

				MimeBodyPart headerPart = new MimeBodyPart();
				headerPart.setDisposition("form-data; name=\"header\"");
				headerPart.setHeader("Content-Type", "application/json");
				System.out.println("part type="+headerPart.getContentType());
				headerPart.addHeader("Content-Length", "526");
				multipart.addBodyPart(headerPart);
				headerPart.setContent(artifact, "application/json");
				ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
				multipart.writeTo(bytesOut);
			 */
			TextMessage textMessage = session.createTextMessage(EntityUtils.toString(entity));
			msgProducer.send(textMessage);
			logger.debug(PRODUCER_SENT_MESSAGE + textMessage.getText());
			
			
			//Create Message for Clearing House
			 gcal = new GregorianCalendar();
			 xgcal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gcal);
			ArrayList<URI> recipientConnectors = new ArrayList<URI>();
			recipientConnectors.add(connectorURI);
			de.fraunhofer.iais.eis.Message idsMessage=new MessageBuilder()
					._modelVersion_("1.0.3")
					._issued_(xgcal)
					._correlationMessage_(message.getId())
					._issuerConnector_(connectorURI)
					._recipientConnectors_(recipientConnectors)
					._senderAgent_(null)
					._recipientAgents_(null)
					._transferContract_(null)
					.build();
			sendMessagetoClearingHouse(idsMessage);
			
			
			
			
			
			
			try {
				Thread.sleep(10000); // sleep for 10 seconds
			} catch (InterruptedException e) {
			}


			//System.out.println("QueueMessageProducer completed");
		} catch (JMSException e) {
			logger.debug("Caught exception: " + e.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	private void sendMessagetoClearingHouse(de.fraunhofer.iais.eis.Message message) {
		try {
			ClientConfig config = new ClientConfig();
			config.connectorProvider(new ApacheConnectorProvider());
			/*
			 * config.property(ClientProperties.PROXY_URI, "proxy_url");
			 * config.property(ClientProperties.PROXY_USERNAME,"user_name");
			 * config.property(ClientProperties.PROXY_PASSWORD,"password");
			 */
			Client client = ClientBuilder.newClient(config);		

			WebTarget webTarget = client.target(CONFIG_PROPERTIES.getProperty("clearingHouseUri"));

			Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);

			/**OLD
		Response response = invocationBuilder.post(Entity.json(new it.eng.idsa.ArtifactRequestMessage(token)));
			 **/
			logger.debug("...sending REST request to the Clearing House...");

			Response response = invocationBuilder.post(Entity.entity(message ,MediaType.APPLICATION_JSON));
			logger.debug("...done "+response.getStatus());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
