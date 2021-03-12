package com.star.onlineshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.star.onlineshopping.dto.PurchaseHistoryDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserDto;
import com.star.onlineshopping.dto.UserReqDto;
import com.star.onlineshopping.dto.UserResDto;
import com.star.onlineshopping.exception.CredentialMissmatchException;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.exception.UserExistException;
import com.star.onlineshopping.service.PurchaseHistoryService;
import com.star.onlineshopping.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PurchaseHistoryService purchaseHistoryService;

	@PostMapping("")
	public ResponseEntity<UserResDto> userRegistration(@RequestBody UserReqDto userReqDto)
			throws UserExistException, CredentialMissmatchException {
		UserResDto userResDto = userService.userRegistration(userReqDto);
		return new ResponseEntity<>(userResDto, HttpStatus.OK);
	}

	@PostMapping("login")
	public ResponseEntity<ResponseDto> userLogin(@RequestBody UserDto userDto) {
		ResponseDto responseDto = userService.userLogin(userDto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PostMapping("buyproduct")
	public ResponseEntity<ResponseDto> buyProduct(@RequestBody PurchaseHistoryDto purchaseHistoryDto)
			throws UserException, ProductException {
		ResponseDto responseDto = purchaseHistoryService.buyProduct(purchaseHistoryDto);
		return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
	}
}
