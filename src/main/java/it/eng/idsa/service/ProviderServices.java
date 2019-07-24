package it.eng.idsa.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.iais.eis.ArtifactRequestMessage;
import it.eng.idsa.DAPSInteraction;
import it.eng.idsa.MessageProducerApp;
import it.eng.idsa.service.util.ServiceResult;
import it.eng.idsa.service.util.PropertiesConfig;



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

	}
}