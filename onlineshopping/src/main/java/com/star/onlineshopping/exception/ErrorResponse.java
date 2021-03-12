/**
 * 
 */
package com.star.onlineshopping.exception;

public class ErrorResponse {

	private String message;

	private int status;
	//for these all generate setter nd getter
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
