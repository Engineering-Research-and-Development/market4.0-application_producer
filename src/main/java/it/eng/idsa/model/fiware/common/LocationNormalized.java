package it.eng.idsa.model.fiware.common;

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
