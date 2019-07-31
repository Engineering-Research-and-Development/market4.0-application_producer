package it.eng.idsa.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.util.SharedByteArrayInputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.iais.eis.ArtifactRequestMessage;
import it.eng.idsa.DAPSInteraction;
import it.eng.idsa.MessageProducerApp;
import it.eng.idsa.service.util.PropertiesConfig;
import it.eng.idsa.service.util.ServiceResult;



@Path("provider")
public class ProviderServices {
	private static final PropertiesConfig CONFIG_PROPERTIES = PropertiesConfig.getInstance();
	private static Logger logger = Logger.getLogger(ProviderServices.class.getName());
	private static final ExecutorService executor = Executors.newFixedThreadPool(5);


	Thread producingThread = new Thread() {
		public void run() {
			try {
				MessageProducerApp messageProducerApp=new MessageProducerApp();
				messageProducerApp.activateProducing();
			} catch(Exception v) {
				logger.debug(v);
			}
		}  
	};

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return CONFIG_PROPERTIES.getProperty("get");
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteIt() {
		//producingThread.interrupt();
		MessageProducerApp messageProducerApp=new MessageProducerApp();
		messageProducerApp.deactivateProducing();
		return CONFIG_PROPERTIES.getProperty("delete");
	}


	@POST
	@Consumes("multipart/mixed")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDataInJSON( String multipartRequestBodyString) {

		ObjectMapper mapper=new ObjectMapper();
		try {
			ServiceResult serviceResult=new ServiceResult();

			ByteArrayDataSource datasource = new ByteArrayDataSource(multipartRequestBodyString, "multipart/form-data");
			MimeMultipart multipart = new MimeMultipart(datasource);
			InputStream in = ((SharedByteArrayInputStream) multipart.getBodyPart(0).getContent()).newStream(0, -1); 
			String body = IOUtils.toString(in, "UTF-8"); 
			//JSON from String to Object
			ArtifactRequestMessage message=mapper.readValue(body, ArtifactRequestMessage.class);

			String token=message.getAuthorizationToken().getTokenValue();
			logger.debug("token ="+token);
			if ((token==null)||(token.isEmpty())) {
				serviceResult.setResult(CONFIG_PROPERTIES.getProperty("ko"));
				serviceResult.setMessage(CONFIG_PROPERTIES.getProperty("tokenNotValid"));
				return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();
			}
			DAPSInteraction dapsInteraction=new DAPSInteraction();
			boolean validationResult=dapsInteraction.validateAuthorization(token, CONFIG_PROPERTIES.getProperty("dapsJWKSUrl"));
			if (validationResult==false) {
				return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();			
			}
			serviceResult.setResult(CONFIG_PROPERTIES.getProperty("ok"));
			executor.execute(producingThread);
			//producingThread.start();
			return Response.status(Response.Status.ACCEPTED.getStatusCode()).entity(serviceResult).build();
			//getTextFromMimeMultipart(multipart);

		}catch(Exception e) {
			e.printStackTrace();
		}


		//ObjectMapper objectMapper=new ObjectMapper();
		//ArtifactRequestMessage artifactRequestMessage=objectMapper.readValue(message, ArtifactRequestMessage.class);

		ServiceResult serviceResult=new ServiceResult();
		serviceResult.setResult("problem");

		return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();

	}



	private String getTextFromMimeMultipart(
			MimeMultipart mimeMultipart) throws IOException, MessagingException {

		int count = mimeMultipart.getCount();
		if (count == 0)
			throw new MessagingException("Multipart with no body parts not supported.");
		boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		if (multipartAlt)
			// alternatives appear in an order of increasing 
			// faithfulness to the original content. Customize as req'd.
			return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		String result = "";
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result += getTextFromBodyPart(bodyPart);
		}
		return result;
	}

	private String getTextFromBodyPart(
			BodyPart bodyPart) throws IOException, MessagingException {

		String result = "";
		if (bodyPart.isMimeType("text/plain")) {
			result = (String) bodyPart.getContent();
		} else if (bodyPart.isMimeType("text/html")) {
			String html = (String) bodyPart.getContent();
			result = org.jsoup.Jsoup.parse(html).text();
		} else if (bodyPart.isMimeType("application/json")) {
			String json = (String) bodyPart.getContent();
			result = json;
		} 
		else if (bodyPart.getContent() instanceof MimeMultipart){
			result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
		}
		return result;
	}




	/*
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDataInJSON(ArtifactRequestMessage artifactRequestMessage) {
		try {
			//System.out.println("message="+message);
			//ObjectMapper objectMapper=new ObjectMapper();
			//ArtifactRequestMessage artifactRequestMessage=objectMapper.readValue(message, ArtifactRequestMessage.class);
			ServiceResult serviceResult=new ServiceResult();
			String token=artifactRequestMessage.getAuthorizationToken().getTokenValue();
			logger.debug("token ="+token);
			if ((token==null)||(token.isEmpty())) {
				serviceResult.setResult(CONFIG_PROPERTIES.getProperty("ko"));
				serviceResult.setMessage(CONFIG_PROPERTIES.getProperty("tokenNotValid"));
				return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();
			}
			DAPSInteraction dapsInteraction=new DAPSInteraction();
			boolean validationResult=dapsInteraction.validateAuthorization(token, CONFIG_PROPERTIES.getProperty("dapsJWKSUrl"));
			if (validationResult==false) {
				return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();			
			}
			serviceResult.setResult(CONFIG_PROPERTIES.getProperty("ok"));
			executor.execute(producingThread);
			//producingThread.start();
			return Response.status(Response.Status.ACCEPTED.getStatusCode()).entity(serviceResult).build();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ServiceResult serviceResult=new ServiceResult();
		serviceResult.setResult("problem");

		return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();

	}*/
}