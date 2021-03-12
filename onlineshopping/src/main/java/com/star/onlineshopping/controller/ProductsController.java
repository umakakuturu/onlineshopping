package com.star.onlineshopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.star.onlineshopping.dto.PraductAddDto;
import com.star.onlineshopping.dto.ProductResDto;
import com.star.onlineshopping.dto.ProductsDto;
import com.star.onlineshopping.dto.RateProductDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.SearchReqDto;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.RateException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.exception.UserNotFoundException;
import com.star.onlineshopping.service.ProductsService;

@RestController
@RequestMapping("products")
public class ProductsController {
	@Autowired
	ProductsService productService;

	@GetMapping("/{emailId}")
	public ResponseEntity<List<ProductsDto>> getProductByEmail(@PathVariable("emailId") String email)
			throws UserException, ProductException {
		List<ProductsDto> productList = productService.getProductByEmail(email);
		return new ResponseEntity<>(productList, HttpStatus.ACCEPTED);
	}

	@PostMapping()
	public ResponseEntity<ResponseDto> addProducts(@RequestBody PraductAddDto praductAddDto)
			throws UserNotFoundException {
		ResponseDto responseDto = productService.addProduct(praductAddDto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@PostMapping("users/{userId}/products/productName")
	public ResponseEntity<List<ProductResDto>> getProductByProductName(@PathVariable("userId") Long userId,
			@RequestBody SearchReqDto searchReqDto) throws ProductException, UserNotFoundException, UserException {
		List<ProductResDto> productList = productService.searchProductByProdName(searchReqDto, userId);
		return new ResponseEntity<>(productList, HttpStatus.ACCEPTED);
	}

	@PostMapping("/rating")
	public ResponseEntity<ResponseDto> rateProduct(@RequestBody RateProductDto rateProductDto)
			throws UserException, RateException {
		ResponseDto responseDto = productService.rateProduct(rateProductDto);
		return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
	}

}
