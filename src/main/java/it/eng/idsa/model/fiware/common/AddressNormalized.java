package it.eng.idsa.model.fiware.common;


/**
 * The AddressNormalized class supports description of FIWARE Data Models objects
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class AddressNormalized {
	private String type;
	private Address value;
	
	public AddressNormalized() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Address getValue() {
		return value;
	}

	public void setValue(Address value) {
		this.value = value;
	}
	
	
}
