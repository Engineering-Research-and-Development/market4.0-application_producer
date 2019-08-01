package it.eng.idsa.model.fiware.common;


/**
 * The Attribute class supports description of FIWARE Data Models objects
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class Attribute {
	private String type;
	private Object value;
	private Metadata metadata;
	
	public Attribute() {}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
		
}
