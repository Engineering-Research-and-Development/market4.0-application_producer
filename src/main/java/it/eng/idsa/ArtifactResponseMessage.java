package it.eng.idsa;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import de.fraunhofer.iais.eis.Token;
import it.eng.idsa.service.util.PropertiesConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ids:ArtifactResponseMessage")
public class ArtifactResponseMessage implements de.fraunhofer.iais.eis.ArtifactResponseMessage {
	private static final PropertiesConfig CONFIG_PROPERTIES = PropertiesConfig.getInstance();

	@Override
	public String getModelVersion() {
		// TODO Auto-generated method stub
		return "1.0.3-SNAPSHOT";
	}

	@Override
	public XMLGregorianCalendar getIssued() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getIssuerConnector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<? extends URI> getRecipientConnectors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<? extends URI> getRecipientAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getCorrelationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getSenderAgent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getSecurityToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getAuthorizationToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getTransferContract() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull URI getId() {
		// TODO Auto-generated method stub
		try {
			return new URI (CONFIG_PROPERTIES.getProperty("uriSchema")+CONFIG_PROPERTIES.getProperty("uriAuthority")+CONFIG_PROPERTIES.getProperty("uriPath")+UUID.randomUUID().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String toRdf() {
		// TODO Auto-generated method stub
		return null;
	}

}
