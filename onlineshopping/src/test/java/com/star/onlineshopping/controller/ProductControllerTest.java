package com.star.onlineshopping.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
import com.star.onlineshopping.utility.ErrorConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {
	@InjectMocks
	ProductsController productController;
	@Mock
	ProductsService productsService;
	ProductsDto productDto = new ProductsDto();
	List<ProductsDto> productList = new ArrayList<ProductsDto>();
	RateProductDto rateProductDto = new RateProductDto();

	@Before
	public void init() {
		productDto.setDescription("football");
		productDto.setName("football");
		productDto.setPrice(2000.23);
		productDto.setProductCode("A123");
		productDto.setType("priority");
		productList.add(productDto);

		rateProductDto.setUserId(1L);
		rateProductDto.setProductId(1L);
		rateProductDto.setRating(5);
	}

	@Test
	public void getProductByEmailTest() throws UserException, ProductException {
		Mockito.when(productsService.getProductByEmail("prateek@gmail")).thenReturn(productList);
		ResponseEntity<List<ProductsDto>> proList = productController.getProductByEmail("prateek@gmail");
		assertEquals(1, proList.getBody().size());

	}

	@Test
	public void addProduct() throws UserNotFoundException {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage(ErrorConstant.PRODUCTS_ADDED_SUCC);

		PraductAddDto praductAddDto = new PraductAddDto();
		praductAddDto.setProductsDto(productList);
		praductAddDto.setUserId(1L);

		Mockito.when(productsService.addProduct(praductAddDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> proList = productController.addProducts(praductAddDto);
		assertEquals(responseDto.getStatusCode(), proList.getStatusCodeValue());

	}

	@Test
	public void getProductByprdName() throws ProductException, UserNotFoundException, UserException {

		SearchReqDto searchReqDto = new SearchReqDto();
		searchReqDto.setProductName("tv");
		Long userId = 1L;

		List<ProductResDto> productResDto = new ArrayList<ProductResDto>();
		ProductResDto productRes = new ProductResDto();
		productRes.setDescription("mobile 16 gb ram");
		productRes.setName("Sony mobile");
		productRes.setPrice(15000d);
		productRes.setProductCode("SY004");
		productRes.setQuantity(4);
		productRes.setRating(4.8f);
		productRes.setType("priority");
		productResDto.add(productRes);

		Mockito.when(productsService.searchProductByProdName(searchReqDto, userId)).thenReturn(productResDto);
		ResponseEntity<List<ProductResDto>> proList = productController.getProductByProductName
				(userId, searchReqDto);
		assertEquals(1, proList.getBody().size());

	}

	@Test
	public void rateTest() throws UserException, RateException {
		ResponseDto responseDto;
		responseDto = new ResponseDto();
		responseDto.setStatusMessage(ErrorConstant.RATE_SUCCESS);
		responseDto.setStatusCode(ErrorConstant.RATE_SUCCESS_CODE);
		Mockito.when(productsService.rateProduct(rateProductDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> actual = productController.rateProduct(rateProductDto);
		assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());
	}
}
