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

import it.eng.idsa.MessageProducerApp;
import it.eng.idsa.service.util.ServiceResult;



@Path("provider")
public class ProviderServices {
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
		return "GET Provider Service: got it!!";
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteIt() {
		//producingThread.interrupt();
		MessageProducerApp messageProducerApp=new MessageProducerApp();
		messageProducerApp.deactivateProducing();
		return "DELETE Provider Service: got it!!";
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDataInJSON(String token) { 
		ServiceResult serviceResult=new ServiceResult();
		logger.debug("token ="+token);
		if ((token==null)||(token.isEmpty())) {
			serviceResult.setResult("KO");
			serviceResult.setMessage("Token not valid!");
			return Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).entity(serviceResult).build();
		}
		serviceResult.setResult("OK");
		executor.execute(producingThread);
		//producingThread.start();
		return Response.status(Response.Status.ACCEPTED.getStatusCode()).entity(serviceResult).build();
	}
}