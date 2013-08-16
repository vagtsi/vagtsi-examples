package de.vagtsi.ligaplan.spielplan.model;

/**
 * Location (gymnasium/sports hall) of one event within the 'Spielplan'.  
 * 
 * @author Jens Vagts
 */
public class SpielplanLocation {
	private String name;
	private String address;
	private String telephoneNumber;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s", getName(), getAddress());
	}
}
