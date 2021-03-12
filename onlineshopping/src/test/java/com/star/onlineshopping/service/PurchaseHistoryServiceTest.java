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

import com.star.onlineshopping.dto.PurchaseHistoryDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserProdoctDto;
import com.star.onlineshopping.entity.Products;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.respository.ProductsRepository;
import com.star.onlineshopping.respository.PurchaseHistoryRepository;
import com.star.onlineshopping.respository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PurchaseHistoryServiceTest {
	@InjectMocks
	PurchaseHistoryServiceImpl purchaseHistoryServiceImpl;
	@Mock
	UserRepository UserRepository;
	@Mock
	ProductsRepository productsRepository;
	@Mock
	PurchaseHistoryRepository purchaseHistoryRepository;
	PurchaseHistoryDto purchaseHistoryDto = new PurchaseHistoryDto();
	List<UserProdoctDto> userProductDtoList = new ArrayList<UserProdoctDto>();
	User user = new User();
	User normalUser = new User();
	UserProdoctDto userProductDto = new UserProdoctDto();
	Products normalProduct = new Products();
	List<Products> productList = new ArrayList<Products>();

	@Before
	public void init() {

		UserProdoctDto userProductDto = new UserProdoctDto();
		userProductDto.setProductId(1L);
		userProductDto.setQuantity(10);

		userProductDtoList.add(userProductDto);

		purchaseHistoryDto.setUserId(1L);
		purchaseHistoryDto.setUserProductDto(userProductDtoList);

		user.setId(1L);
		user.setName("prateek");
		user.setPassword("pal");

		user.setPhoneNumber("1234");
		user.setEmail("pal@gmail.com");
		user.setUserType("priority");

		normalUser.setId(1L);
		normalUser.setName("prateek");
		normalUser.setPassword("pal");

		normalUser.setPhoneNumber("1234");
		normalUser.setEmail("lol@gmail.com");
		normalUser.setUserType("normal");

		normalProduct.setId(1L);
		normalProduct.setDescription("cricket");
		normalProduct.setName("bat");
		normalProduct.setPrice(2000.23);
		normalProduct.setProductCode("B123");
		normalProduct.setType("normal");
		normalProduct.setRating(5);
		normalProduct.setQuantity(100);
		// productList.add(product);
		productList.add(normalProduct);

	}

	@Test
	public void buyProductTest() throws UserException, ProductException {
		Mockito.when(UserRepository.findById(1L)).thenReturn(Optional.of(normalUser));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(normalProduct));
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(purchaseHistoryDto);
		assertEquals("ordered successfully", actual.getStatusMessage());
	}

	@Test
	public void buyProductPriorityUserTest() throws UserException, ProductException {
		Mockito.when(UserRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(normalProduct));
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(purchaseHistoryDto);
		assertEquals("ordered successfully", actual.getStatusMessage());
	}

	@Test(expected = UserException.class)
	public void invalidUserTest() throws UserException, ProductException {

		Mockito.when(UserRepository.findById(10L)).thenReturn(Optional.of(normalUser));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(normalProduct));
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(purchaseHistoryDto);
		assertEquals("ordered successfully", actual.getStatusMessage());

	}

	@Test(expected = ProductException.class)
	public void outOfStockTest() throws UserException, ProductException {
		Mockito.when(UserRepository.findById(1L)).thenReturn(Optional.of(normalUser));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(normalProduct));

		UserProdoctDto userProdoct = new UserProdoctDto();
		userProdoct.setProductId(1L);
		userProdoct.setQuantity(1000);
		List<UserProdoctDto> userProductList = new ArrayList<UserProdoctDto>();
		userProductList.add(userProdoct);
		PurchaseHistoryDto ph = new PurchaseHistoryDto();
		ph.setUserId(1L);
		ph.setUserProductDto(userProductList);
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(ph);

	}

	@Test(expected = ProductException.class)
	public void outOfStockPriorityUser() throws UserException, ProductException {
		Mockito.when(UserRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(normalProduct));

		UserProdoctDto userProdoct = new UserProdoctDto();
		userProdoct.setProductId(1L);
		userProdoct.setQuantity(1000);
		List<UserProdoctDto> userProductList = new ArrayList<UserProdoctDto>();
		userProductList.add(userProdoct);
		PurchaseHistoryDto ph = new PurchaseHistoryDto();
		ph.setUserId(1L);
		ph.setUserProductDto(userProductList);
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(ph);

	}

	@Test(expected = ProductException.class)
	public void productNotFoundException() throws UserException, ProductException {
		Products p = new Products();
		Mockito.when(UserRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(productsRepository.findById(1L)).thenReturn(Optional.of(p));

		UserProdoctDto userProdoct = new UserProdoctDto();
		userProdoct.setProductId(1L);
		userProdoct.setQuantity(1000);
		List<UserProdoctDto> userProductList = new ArrayList<UserProdoctDto>();
		userProductList.add(userProdoct);
		PurchaseHistoryDto ph = new PurchaseHistoryDto();
		ph.setUserId(1L);
		ph.setUserProductDto(userProductList);
		ResponseDto actual = purchaseHistoryServiceImpl.buyProduct(ph);

	}

}
