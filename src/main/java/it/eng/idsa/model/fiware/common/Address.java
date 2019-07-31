package it.eng.idsa.model.fiware.common;

public class Address {
	private String streetAddress;
	private String addressLocality;
	private String addressRegion;
	private String addressCountry;
	private String postalCode;
	private String postOfficeBoxNumber;
	private String areaServed;
	
	public Address() {}
	
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getAddressLocality() {
		return addressLocality;
	}
	public void setAddressLocality(String addressLocality) {
		this.addressLocality = addressLocality;
	}
	public String getAddressRegion() {
		return addressRegion;
	}
	public void setAddressRegion(String addressRegion) {
		this.addressRegion = addressRegion;
	}
	public String getAddressCountry() {
		return addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPostOfficeBoxNumber() {
		return postOfficeBoxNumber;
	}
	public void setPostOfficeBoxNumber(String postOfficeBoxNumber) {
		this.postOfficeBoxNumber = postOfficeBoxNumber;
	}
	public String getAreaServed() {
		return areaServed;
	}
	public void setAreaServed(String areaServed) {
		this.areaServed = areaServed;
	}

	
}
