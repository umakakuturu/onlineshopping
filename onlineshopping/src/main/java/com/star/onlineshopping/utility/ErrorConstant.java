package com.star.onlineshopping.utility;

public class ErrorConstant {
private ErrorConstant() {
}

	public static final String NO_RECORD_FOUND = "no record found";
	public static final int NO_RECORD_FOUND_CODE = 600;

	public static final String NO_EMAIL_FOUND = "no email found";
	public static final int NO_EMAIL_FOUND_CODE = 601;

	public static final String INVALID_INPUT = "Please provide the required  data";
	public static final int INVALID_INPUT_CODE = 602;

	public static final String LOGIN_SUCCESS = "logged in successfully";
	public static final int LOGIN_SUCCESS_CODE = 603;

	public static final String LOGIN_FAIL = "invalid username or password";
	public static final int LOGIN_FAIL_CODE = 604;

	public static final String USER_EXIST = "already registered";
	public static final int USER_EXIST_CODE = 605;

	public static final String CREDENTIAL_MISSMATCH = "password and confirm password must be same";
	public static final int CREDENTIAL_MISSMATCH_CODE = 606;

	public static final String USER_REGISTERED = "registered successfully";
	public static final int USER_REGISTERED_CODE = 607;

	public static final String USER_NOT_FOUND = "User not found";
	public static final int USER_NOT_FOUND_CODE = 608;

	public static final String PRODUCTS_ADDED_SUCC = "Products added successfully";
	public static final int PRODUCTS_ADDED_SUCC_CODE = 200;

	public static final String USER_NOT_ADMIN = "Only admin can add the products";
	public static final int USER_NOT_ADMIN_CODE = 609;

	public static final String INSUFFICIANT_ITEM = "out of stock";
	public static final int INSUFFICIANT_ITEM_CODE = 608;

	public static final String ORDER_SUCCESS = "ordered successfully";
	public static final int ORDER_SUCCESS_CODE = 609;

	public static final String RATE_SUCCESS = "rated successfully";
	public static final int RATE_SUCCESS_CODE = 610;

	public static final String BUY_ERROR = "buy product first";
	public static final int BUY_ERROR_CODE = 611;

}
