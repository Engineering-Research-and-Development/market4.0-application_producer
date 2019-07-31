package it.eng.idsa.model.fiware.common;

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
