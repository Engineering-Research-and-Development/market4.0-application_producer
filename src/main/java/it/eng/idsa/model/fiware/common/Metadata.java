package it.eng.idsa.model.fiware.common;


/**
 * The Metadata class supports description of FIWARE Data Models objects
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class Metadata {
	private TimeInstant TimeInstant;
	
	public Metadata() {}

	public TimeInstant getTimeInstant() {
		return TimeInstant;
	}

	public void setTimeInstant(TimeInstant timeInstant) {
		TimeInstant = timeInstant;
	};
	
}
