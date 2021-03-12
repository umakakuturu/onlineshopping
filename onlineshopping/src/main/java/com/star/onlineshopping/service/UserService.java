package com.star.onlineshopping.service;

import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserDto;
import com.star.onlineshopping.dto.UserReqDto;
import com.star.onlineshopping.dto.UserResDto;
import com.star.onlineshopping.exception.CredentialMissmatchException;
import com.star.onlineshopping.exception.UserExistException;

public interface UserService {

	public ResponseDto userLogin(UserDto userDto);

	public UserResDto userRegistration(UserReqDto userReqDto) 
			throws UserExistException, CredentialMissmatchException;

}
