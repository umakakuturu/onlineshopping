package com.star.onlineshopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserDto;
import com.star.onlineshopping.dto.UserReqDto;
import com.star.onlineshopping.dto.UserResDto;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.CredentialMissmatchException;
import com.star.onlineshopping.exception.UserExistException;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Before
	public void init() {

	}

	@Test
	public void userLoginTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("prateek");
		userDto.setPassword("pal");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.LOGIN_SUCCESS_CODE);
		responseDto.setStatusMessage("Logged in successfully!!..");

		User user = new User();
		user.setEmail("prateek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		ResponseDto res = userServiceImpl.userLogin(userDto);
		assertEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test
	public void InvalidUserLoginTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("");
		userDto.setPassword("");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.INVALID_INPUT_CODE);
		responseDto.setStatusMessage("Please provide the required  data");

		User user = new User();
		user.setEmail("prateek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		ResponseDto res = userServiceImpl.userLogin(userDto);
		assertEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test
	public void InvalidUserLoginUserTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("");
		userDto.setPassword("pal");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.INVALID_INPUT_CODE);
		responseDto.setStatusMessage("Please provide the required  data");

		User user = new User();
		user.setEmail("prateek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		ResponseDto res = userServiceImpl.userLogin(userDto);
		assertEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test
	public void InvalidUserLogiPasswordTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("pal");
		userDto.setPassword("");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.INVALID_INPUT_CODE);
		responseDto.setStatusMessage("Please provide the required  data");

		User user = new User();
		user.setEmail("prateek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		ResponseDto res = userServiceImpl.userLogin(userDto);
		assertEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test
	public void NoUserLoginTest() {

		UserDto userDto = new UserDto();
		userDto.setEmail("pratreek");
		userDto.setPassword("pal");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.LOGIN_FAIL_CODE);
		responseDto.setStatusMessage("Please Register");

		User user = new User();
		user.setEmail("pratteek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		Mockito.when(userRepository.findByEmailAndPassword("pal", "pal")).thenReturn(user);
		ResponseDto res = userServiceImpl.userLogin(userDto);
		assertEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test(expected = UserExistException.class)
	public void userRegistrationTest() throws UserExistException, CredentialMissmatchException {

		UserResDto res = new UserResDto();
		res.setStatusCode(HttpStatus.ALREADY_REPORTED.value());

		UserReqDto userReqDto = new UserReqDto();
		userReqDto.setConfirmPassword("nagajyoti@123");
		userReqDto.setPassword("nagajyoti@123");
		userReqDto.setEmail("nagajyoti@gmail.com");
		userReqDto.setName("Nagajyoti");
		userReqDto.setUserType("Priority");
		userReqDto.setPhoneNumber("9980111546");
		User user = new User();
		user.setConfirmPassword("nagajyoti@123");
		user.setPassword("nagajyoti@123");
		user.setEmail("nagajyoti@gmail.com");
		user.setPhoneNumber("9980111546");
		user.setName("Nagajyoti");
		user.setUserType("Priority");

		Mockito.when(userRepository.findByEmailAndPhoneNumber(userReqDto.getEmail(), userReqDto.getPhoneNumber()))
				.thenReturn(user);

		UserResDto userResDto = userServiceImpl.userRegistration(userReqDto);
		assertNotEquals(res.getStatusCode(), userResDto.getStatusCode());

	}

	@Test(expected = CredentialMissmatchException.class)
	public void userCredentialMissMatchTest() throws UserExistException, CredentialMissmatchException {

		UserResDto res = new UserResDto();
		res.setStatusCode(HttpStatus.UNAUTHORIZED.value());

		UserReqDto userReqDto = new UserReqDto();
		userReqDto.setConfirmPassword("nagajyoti@123");
		userReqDto.setPassword("nagajyoti45@123");
		userReqDto.setEmail("nagajyoti@gmail.com");
		userReqDto.setName("Nagajyoti");
		userReqDto.setUserType("Priority");
		userReqDto.setPhoneNumber("9980111546");

		UserResDto result = userServiceImpl.userRegistration(userReqDto);
		assertNotEquals(res.getStatusCode(), result.getStatusCode());

	}

	@Test
	public void userRegistrationSuccessTest() throws UserExistException, CredentialMissmatchException {

		UserResDto res = new UserResDto();
		res.setStatusCode(ErrorConstant.CREDENTIAL_MISSMATCH_CODE);

		UserReqDto userReqDto = new UserReqDto();
		userReqDto.setConfirmPassword("nagajyoti@123");
		userReqDto.setPassword("nagajyoti@123");
		userReqDto.setEmail("nagajyoti@gmail.com");
		userReqDto.setName("Nagajyoti");
		userReqDto.setUserType("Priority");
		userReqDto.setPhoneNumber("9980111546");

		User user = new User();
		user.setConfirmPassword("nagajyoti@123");
		user.setPassword("nagajyoti@123");
		user.setEmail("nagajyoti@gmail.com");
		user.setPhoneNumber("9980111546");
		user.setName("Nagajyoti");
		user.setUserType("Priority");

		Mockito.when(userRepository.save(user)).thenReturn(user);

		UserResDto result = userServiceImpl.userRegistration(userReqDto);
		assertNotEquals(res.getStatusCode(), result.getStatusCode());

	}

}
