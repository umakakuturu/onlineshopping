package com.star.onlineshopping.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.star.onlineshopping.dto.PurchaseHistoryDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserDto;
import com.star.onlineshopping.dto.UserProdoctDto;
import com.star.onlineshopping.dto.UserReqDto;
import com.star.onlineshopping.dto.UserResDto;
import com.star.onlineshopping.exception.CredentialMissmatchException;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.exception.UserExistException;
import com.star.onlineshopping.service.PurchaseHistoryService;
import com.star.onlineshopping.service.UserService;
import com.star.onlineshopping.utility.ErrorConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	@Mock
	UserService userService;

	@Mock
	PurchaseHistoryService purchaseHistoryService;

	@Test
	public void userRegistration() throws UserExistException, CredentialMissmatchException {
		UserReqDto userReqDto = new UserReqDto();
		userReqDto.setConfirmPassword("nagajyoti@123");
		userReqDto.setPassword("nagajyoti@123");
		userReqDto.setEmail("nagajyoti@gmail.com");
		userReqDto.setName("Nagajyoti");
		userReqDto.setUserType("Priority");
		userReqDto.setPhoneNumber("9980111546");

		UserResDto responseDto = new UserResDto();
		responseDto.setStatusCode(ErrorConstant.USER_REGISTERED_CODE);
		responseDto.setMessage(ErrorConstant.USER_REGISTERED + " " + 
		userReqDto.getEmail() + " " + "Is your User Name");

		Mockito.when(userService.userRegistration(userReqDto)).thenReturn(responseDto);
		ResponseEntity<UserResDto> result = userController.userRegistration(userReqDto);

		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	public void userLoginTest() {
		UserDto userDto = new UserDto();
		userDto.setEmail("abc@gmail.com");
		userDto.setPassword("asdf");

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.INVALID_INPUT_CODE);
		responseDto.setStatusMessage(ErrorConstant.INVALID_INPUT);

		Mockito.when(userService.userLogin(userDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> result = userController.userLogin(userDto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void buyProduct() throws UserException, ProductException {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(ErrorConstant.ORDER_SUCCESS_CODE);
		responseDto.setStatusMessage(ErrorConstant.ORDER_SUCCESS);

		PurchaseHistoryDto purchaseHistoryDto = new PurchaseHistoryDto();
		UserProdoctDto userProductDto = new UserProdoctDto();
		userProductDto.setProductId(1L);
		userProductDto.setQuantity(1);
		;
		List<UserProdoctDto> userProductDtoList = new ArrayList<UserProdoctDto>();
		userProductDtoList.add(userProductDto);
		purchaseHistoryDto.setUserId(1L);
		purchaseHistoryDto.setUserProductDto(userProductDtoList);

		Mockito.when(purchaseHistoryService.buyProduct(purchaseHistoryDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> result = userController.buyProduct(purchaseHistoryDto);
		assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
	}

}