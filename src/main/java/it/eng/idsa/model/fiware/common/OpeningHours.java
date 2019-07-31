package it.eng.idsa.model.fiware.common;

public class OpeningHours {
	private String dayOfWeek;
	private String closes;
	private String opens;
	
	public OpeningHours() {}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getCloses() {
		return closes;
	}

	public void setCloses(String closes) {
		this.closes = closes;
	}

	public String getOpens() {
		return opens;
	}

	public void setOpens(String opens) {
		this.opens = opens;
	}
	
}
