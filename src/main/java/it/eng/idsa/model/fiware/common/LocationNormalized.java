package it.eng.idsa.model.fiware.common;


/**
 * The LocationNormalized class supports description of FIWARE Data Models objects
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class LocationNormalized {
	private String type;
	private Location value;
	
	public LocationNormalized() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Location getValue() {
		return value;
	}

	public void setValue(Location value) {
		this.value = value;
	}
	
}
