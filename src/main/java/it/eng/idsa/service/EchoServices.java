package it.eng.idsa.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import it.eng.idsa.service.util.ServiceResult;
@Path("echo")
public class EchoServices {
	private static Logger logger = Logger.getLogger(EchoServices.class.getName());

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "ECHO TEST RESULT";
	}
	
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDataInJSON( String test) {
		logger.debug("ECHO POST input="+test);
		ServiceResult serviceResult=new ServiceResult();
		serviceResult.setMessage("OK");
		serviceResult.setResult("TEST RESULT");
		return Response.status(Response.Status.ACCEPTED.getStatusCode()).entity(serviceResult).build();

	}
}
