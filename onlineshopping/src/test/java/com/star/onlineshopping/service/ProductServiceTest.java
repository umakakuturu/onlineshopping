package com.star.onlineshopping.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
import org.springframework.http.HttpStatus;

import com.star.onlineshopping.dto.PraductAddDto;
import com.star.onlineshopping.dto.ProductResDto;
import com.star.onlineshopping.dto.ProductsDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.SearchReqDto;
import com.star.onlineshopping.entity.Products;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.exception.UserNotFoundException;
import com.star.onlineshopping.respository.ProductsRepository;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTest {
	@InjectMocks
	ProductsServiceImpl productsServiceImpl;

	@Mock
	ProductsRepository productRepository;
	@Mock
	UserRepository userRepository;
	User user = new User();
	User normalUser = new User();
	Products product = new Products();
	Products normalProduct = new Products();
	List<Products> productList = new ArrayList<Products>();
	ProductsDto productDto = new ProductsDto();
	List<ProductsDto> productDtoList = new ArrayList<ProductsDto>();

	@Before
	public void init() {
		user.setId(1L);
		user.setName("prateek");
		user.setPassword("pal");
		user.setConfirmPassword("pal");
		user.setPhoneNumber("1234");
		user.setEmail("pal@gmail.com");
		user.setUserType("priority");

		normalUser.setId(2L);
		normalUser.setName("prateek");
		normalUser.setPassword("pal");
		normalUser.setConfirmPassword("pal");
		normalUser.setPhoneNumber("1234");
		normalUser.setEmail("lol@gmail.com");
		normalUser.setUserType("normal");

		product.setId(1L);
		product.setDescription("football");
		product.setName("football");
		product.setPrice(2000.23);
		product.setQuantity(4);
		product.setProductCode("A123");
		product.setType("priority");

		normalProduct.setId(2L);
		normalProduct.setDescription("cricket");
		normalProduct.setName("bat");
		normalProduct.setPrice(2000.23);
		normalProduct.setProductCode("B123");
		normalProduct.setType("normal");
		productList.add(product);
		productList.add(normalProduct);

		productDto.setDescription("football");
		productDto.setName("football");
		productDto.setPrice(2000.23);
		productDto.setProductCode("A123");
		productDto.setType("priority");

		productDtoList.add(productDto);

	}

	@Test
	public void getPriorityProductTest() throws UserException, ProductException {
		Mockito.when(userRepository.findByEmail("pal@gmail.com")).thenReturn(java.util.Optional.of(user));
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		List<ProductsDto> l = productsServiceImpl.getProductByEmail("pal@gmail.com");
		assertEquals(2, l.size());
	}

	@Test
	public void getNormalProductTest() throws UserException, ProductException {
		Mockito.when(userRepository.findByEmail("lol@gmail.com")).thenReturn(java.util.Optional.of(normalUser));
		Mockito.when(productRepository.findByType(normalUser.getUserType())).thenReturn(productList);
		List<ProductsDto> l = productsServiceImpl.getProductByEmail("lol@gmail.com");
		assertEquals(2, l.size());
	}

	@Test(expected = UserException.class)
	public void invalidUserTest() throws UserException, ProductException {
		User user = new User();
		user.setId(1L);
		Mockito.when(userRepository.findByEmail("lol@gmail.com")).thenReturn(java.util.Optional.of(user));
		List<ProductsDto> l = productsServiceImpl.getProductByEmail("lola@gmail.com");

	}

	@Test(expected = ProductException.class)
	public void noRecordFoundTest() throws UserException, ProductException {
		List<Products> productList = new ArrayList<Products>();
		Mockito.when(userRepository.findByEmail("pal@gmail.com")).thenReturn(java.util.Optional.of(user));
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		List<ProductsDto> l = productsServiceImpl.getProductByEmail("pal@gmail.com");

	}

	@Test(expected = UserNotFoundException.class)
	public void noRecordFoundForUser() throws UserNotFoundException {

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage(ErrorConstant.PRODUCTS_ADDED_SUCC);

		PraductAddDto praductAddDto = new PraductAddDto();

		List<ProductsDto> productList = new ArrayList<>();
		ProductsDto productDto = new ProductsDto();
		praductAddDto.setUserId(6L);
		productDto.setDescription("football");
		productDto.setName("football");
		productDto.setPrice(2000.23);
		productDto.setProductCode("A123");
		productDto.setType("priority");
		productList.add(productDto);
		Mockito.when(userRepository.findById(praductAddDto.getUserId())).thenReturn(Optional.of(user));
		ResponseDto res = productsServiceImpl.addProduct(praductAddDto);

	}

	@Test
	public void addProductSucc() throws UserNotFoundException {

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage(ErrorConstant.PRODUCTS_ADDED_SUCC);

		PraductAddDto praductAddDto = new PraductAddDto();

		List<ProductsDto> productList = new ArrayList<>();
		ProductsDto productDto = new ProductsDto();
		praductAddDto.setUserId(6L);
		productDto.setDescription("football");
		productDto.setName("football");
		productDto.setPrice(2000.23);
		productDto.setProductCode("A123");
		productDto.setType("priority");
		productList.add(productDto);

		Mockito.when(userRepository.findById(praductAddDto.getUserId())).thenReturn(Optional.of(user));

		Mockito.when(productRepository.save(product)).thenReturn(product);
		ResponseDto res = productsServiceImpl.addProduct(praductAddDto);

		assertNotEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test
	public void addProductNoAdmin() throws UserNotFoundException {

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage(ErrorConstant.USER_NOT_ADMIN);

		PraductAddDto praductAddDto = new PraductAddDto();

		List<ProductsDto> productList = new ArrayList<>();
		ProductsDto productDto = new ProductsDto();
		praductAddDto.setUserId(6L);
		productDto.setDescription("football");
		productDto.setName("football");
		productDto.setPrice(2000.23);
		productDto.setProductCode("A123");
		productDto.setType("priority");
		productList.add(productDto);

		Mockito.when(userRepository.findById(praductAddDto.getUserId())).thenReturn(Optional.of(user));

		ResponseDto res = productsServiceImpl.addProduct(praductAddDto);

		assertNotEquals(responseDto.getStatusCode(), res.getStatusCode());

	}

	@Test(expected = UserNotFoundException.class)
	public void userNotFound() throws ProductException, UserNotFoundException, UserException {
		User user = new User();
		user.setId(6L);

		SearchReqDto searchReqDto = new SearchReqDto();
		searchReqDto.setProductName("tv");

		Mockito.when(userRepository.findById(6L)).thenReturn(Optional.of(user));
		List<ProductResDto> l = productsServiceImpl.searchProductByProdName(searchReqDto, 6L);

	}

	@Test
	public void getPriorityProductByName() throws ProductException, UserNotFoundException, UserException {
		SearchReqDto searchReqDto = new SearchReqDto();
		searchReqDto.setProductName("tv");

		List<ProductResDto> ProductResDto = new ArrayList<ProductResDto>();
		ProductResDto productResDto = new ProductResDto();

		productResDto.setDescription("football");
		productResDto.setName("football");
		productResDto.setPrice(2000.23);
		productResDto.setQuantity(4);
		productResDto.setProductCode("A123");
		productResDto.setType("priority");
		ProductResDto.add(productResDto);

		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findByName(searchReqDto.getProductName())).thenReturn(productList);
		List<ProductResDto> l = productsServiceImpl.searchProductByProdName(searchReqDto, 2L);
		assertEquals(2, l.size());
	}

	@Test(expected = ProductException.class)
	public void getNormalProductByNameExc() throws ProductException, UserNotFoundException, UserException {
		SearchReqDto searchReqDto = new SearchReqDto();
		searchReqDto.setProductName("tv");

		List<ProductResDto> ProductResDto = new ArrayList<ProductResDto>();
		ProductResDto productResDto = new ProductResDto();

		productResDto.setDescription("tv");
		productResDto.setName("tv");
		productResDto.setPrice(2000.23);
		productResDto.setQuantity(4);
		productResDto.setProductCode("A123");
		productResDto.setType("priority");
		ProductResDto.add(productResDto);

		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findByNameAndType("normal", searchReqDto.getProductName()))
				.thenReturn(productList);
		List<ProductResDto> l = productsServiceImpl.searchProductByProdName(searchReqDto, 2L);
	}

	@Test
	public void getNormalProductByName() throws ProductException, UserNotFoundException, UserException {
		SearchReqDto searchReqDto = new SearchReqDto();
		searchReqDto.setProductName("tv");

		List<ProductResDto> ProductResDto = new ArrayList<ProductResDto>();
		ProductResDto productResDto = new ProductResDto();

		productResDto.setDescription("tv");
		productResDto.setName("tv");
		productResDto.setPrice(2000.23);
		productResDto.setQuantity(4);
		productResDto.setProductCode("A123");
		productResDto.setType("normal");
		ProductResDto.add(productResDto);

		Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(user));
		Mockito.when(productRepository.findByNameAndType(normalUser.getUserType(), searchReqDto.getProductName()))
				.thenReturn(productList);
		List<ProductResDto> l = productsServiceImpl.searchProductByProdName(searchReqDto, 3L);
		assertEquals(2, l.size());
	}

	@Test(expected = ProductException.class)
	public void noRecordFoundForNormalTest() throws UserException, ProductException {
		List<Products> productList = new ArrayList<Products>();
		Mockito.when(userRepository.findByEmail("lol@gmail.com")).thenReturn(java.util.Optional.of(normalUser));
		Mockito.when(productRepository.findByType(normalUser.getUserType())).thenReturn(productList);
		List<ProductsDto> l = productsServiceImpl.getProductByEmail("lol@gmail.com");

	}
}
