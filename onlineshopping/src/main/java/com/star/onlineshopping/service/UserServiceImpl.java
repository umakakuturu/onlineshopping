package com.star.onlineshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserDto;
import com.star.onlineshopping.dto.UserReqDto;
import com.star.onlineshopping.dto.UserResDto;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.CredentialMissmatchException;
import com.star.onlineshopping.exception.UserExistException;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserResDto userRegistration(UserReqDto userReqDto) throws 
	UserExistException, CredentialMissmatchException {

		UserResDto userResDto = new UserResDto();
		User user;
		user = userRepository.findByEmailAndPhoneNumber(userReqDto.getEmail(), userReqDto.getPhoneNumber());

		if (user != null) {

			throw new UserExistException(ErrorConstant.USER_EXIST);
		} else {
			if (!(userReqDto.getPassword().equals(userReqDto.getConfirmPassword())))
				throw new CredentialMissmatchException(ErrorConstant.CREDENTIAL_MISSMATCH);
			user = new User();
			user.setConfirmPassword(userReqDto.getConfirmPassword());
			user.setPassword(userReqDto.getPassword());
			user.setEmail(userReqDto.getEmail());
			user.setPhoneNumber(userReqDto.getPhoneNumber());
			user.setName(userReqDto.getPhoneNumber());
			user.setUserType(userReqDto.getUserType());
			userRepository.save(user);
			userResDto.setStatusCode(ErrorConstant.USER_REGISTERED_CODE);
			userResDto.setMessage(ErrorConstant.USER_REGISTERED);
			userResDto.setUserName(userReqDto.getEmail() + "is your username");

		}
		return userResDto;

	}

	@Override
	public ResponseDto userLogin(UserDto userDto) {
		ResponseDto responseDto = new ResponseDto();

		if (userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
			responseDto.setStatusCode(ErrorConstant.INVALID_INPUT_CODE);
			responseDto.setStatusMessage(ErrorConstant.INVALID_INPUT);

		} else {
			User user = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

			if (user != null) {
				responseDto.setStatusCode(ErrorConstant.LOGIN_SUCCESS_CODE);
				responseDto.setStatusMessage(ErrorConstant.LOGIN_SUCCESS);
			} else {
				responseDto.setStatusCode(ErrorConstant.LOGIN_FAIL_CODE);
				responseDto.setStatusMessage(ErrorConstant.LOGIN_FAIL);

			}
		}
		return responseDto;
	}
}