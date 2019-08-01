package it.eng.idsa.service.util;


/**
 * The ServiceResult class is responsible for creating a common REST service response
 * 
 * 
 * @author  Gabriele De Luca, Milan Karajovic
 */
public class ServiceResult {
	private String result;
	private String message;
	
	public ServiceResult() {}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
