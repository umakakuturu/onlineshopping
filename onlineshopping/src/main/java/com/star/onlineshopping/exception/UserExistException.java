/**
 * 
 */
package com.star.onlineshopping.exception;

/**
 * @author User1
 *
 */
public class UserExistException extends Exception{
	
	private static final long serialVersionUID = -1747502351308293745L;

	public UserExistException(String message) {
		super(message);
	}

}
