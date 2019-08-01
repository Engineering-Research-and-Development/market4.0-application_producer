package it.eng.idsa.model.fiware.common;

import java.util.List;

/**
 * The Location class supports description of FIWARE Data Models objects
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class Location {
	private String type;
	private List<Object> coordinates ;
	private List<Float> bbox;
	
	public Location() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Object> coordinates) {
		this.coordinates = coordinates;
	}

	public List<Float> getBbox() {
		return bbox;
	}

	public void setBbox(List<Float> bbox) {
		this.bbox = bbox;
	}
	
}
