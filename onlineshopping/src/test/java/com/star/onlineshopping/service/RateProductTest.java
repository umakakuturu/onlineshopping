package com.star.onlineshopping.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.star.onlineshopping.dto.RateProductDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.entity.Products;
import com.star.onlineshopping.entity.PurchaseHistory;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.RateException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.respository.ProductsRepository;
import com.star.onlineshopping.respository.PurchaseHistoryRepository;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RateProductTest {
	@InjectMocks
	ProductsServiceImpl productsServiceImpl;
	@Mock
	PurchaseHistoryRepository purchaseHistoryRepository;
	@Mock
	ProductsRepository productRepository;
	@Mock
	UserRepository userRepository;

	User user = new User();

	Products product = new Products();
	PurchaseHistory purchaseHistory = new PurchaseHistory();
	List<PurchaseHistory> purchaseHistoryList = new ArrayList<PurchaseHistory>();
	RateProductDto rateProductDto = new RateProductDto();
	ResponseDto responseDto;

	@Before
	public void init() {

		user.setEmail("prateek");
		user.setPassword("pal");
		user.setPhoneNumber("1234");
		user.setUserType("normal");

		product.setId(1L);
		product.setDescription("football");
		product.setName("football");
		product.setPrice(2000.00);
		product.setProductCode("A123");
		product.setType("normal");
		product.setRating(5);
		product.setQuantity(100);

		purchaseHistory.setId(1);
		purchaseHistory.setPrice(600);
		purchaseHistory.setQuantity(3);
		purchaseHistory.setProducts(product);
		purchaseHistory.setRating(0);
		purchaseHistory.setUser(user);

		rateProductDto.setUserId(1L);
		rateProductDto.setProductId(1L);
		rateProductDto.setRating(5);

		purchaseHistoryList.add(purchaseHistory);
		user.setBuyProduct(purchaseHistoryList);
		product.setBuyProduct(purchaseHistoryList);

		responseDto = new ResponseDto();
		responseDto.setStatusMessage(ErrorConstant.RATE_SUCCESS);
		responseDto.setStatusCode(ErrorConstant.RATE_SUCCESS_CODE);
	}

	@Test
	public void rateTest() throws UserException, RateException {
		// Mockito.when(productsServiceImpl.rateProduct(rateProductDto)).thenReturn(responseDto);
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		Mockito.when(purchaseHistoryRepository.save(purchaseHistory)).thenReturn(purchaseHistory);
		ResponseDto actual = productsServiceImpl.rateProduct(rateProductDto);
		assertEquals(ErrorConstant.RATE_SUCCESS, actual.getStatusMessage());
	}

	@Test(expected = UserException.class)
	public void InvalidUserRateTest() throws UserException, RateException {
		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
		// Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		// Mockito.when(purchaseHistoryRepository.save(purchaseHistory)).thenReturn(purchaseHistory);
		ResponseDto actual = productsServiceImpl.rateProduct(rateProductDto);
	}

	@Test(expected = UserException.class)
	public void InvalidProductRateTest() throws UserException, RateException {
		Mockito.when(productRepository.findById(2L)).thenReturn(Optional.of(product));
		// Mockito.when(purchaseHistoryRepository.save(purchaseHistory)).thenReturn(purchaseHistory);
		ResponseDto actual = productsServiceImpl.rateProduct(rateProductDto);
	}

	@Test(expected = RateException.class)
	public void notPurchasedProductTest() throws UserException, RateException {
		User u = new User();
		u.setEmail("prateek");
		u.setPassword("pal");
		u.setPhoneNumber("1234");
		u.setUserType("normal");
		List<PurchaseHistory> list = new ArrayList<>();
		// list.add(new PurchaseHistory());
		u.setBuyProduct(list);
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(u));
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		Mockito.when(purchaseHistoryRepository.save(purchaseHistory)).thenReturn(purchaseHistory);
		ResponseDto actual = productsServiceImpl.rateProduct(rateProductDto);
	}

}
