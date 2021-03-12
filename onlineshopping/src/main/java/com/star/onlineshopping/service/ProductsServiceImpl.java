package com.star.onlineshopping.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.onlineshopping.dto.PraductAddDto;
import com.star.onlineshopping.dto.ProductResDto;
import com.star.onlineshopping.dto.ProductsDto;
import com.star.onlineshopping.dto.RateProductDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.SearchReqDto;
import com.star.onlineshopping.entity.Products;
import com.star.onlineshopping.entity.PurchaseHistory;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.RateException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.exception.UserNotFoundException;
import com.star.onlineshopping.respository.ProductsRepository;
import com.star.onlineshopping.respository.PurchaseHistoryRepository;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@Service
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	ProductsRepository productRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PurchaseHistoryRepository purchaseHistoryRepository;

	@Override
	public List<ProductsDto> getProductByEmail(String email) throws UserException, ProductException {

		Optional<User> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			throw new UserException(ErrorConstant.NO_EMAIL_FOUND);
		}

		if (user.get().getUserType().equals("priority")) {
			List<Products> productList = productRepository.findAll();

			List<ProductsDto> productsDtos = productList.stream().map(product -> {
				ProductsDto dto = new ProductsDto();
				BeanUtils.copyProperties(product, dto);
				return dto;
			}).collect(Collectors.toList());
			if (productsDtos.isEmpty())
				throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
			else
				return productsDtos;
		} else {
			List<Products> productList = productRepository.findByType(user.get().getUserType());
			if (productList.isEmpty())
				throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
			else {
				List<ProductsDto> productsDtos = productList.stream().map(product -> {
					ProductsDto dto = new ProductsDto();
					BeanUtils.copyProperties(product, dto);
					return dto;
				}).collect(Collectors.toList());
				return productsDtos;
			}

		}

	}

	@Override
	public ResponseDto addProduct(PraductAddDto praductAddDto) throws UserNotFoundException {

		Optional<User> user = userRepository.findById(praductAddDto.getUserId());

		Products product = new Products();

		ResponseDto responseDto = new ResponseDto();

		if (!user.isPresent())
			throw new UserNotFoundException(ErrorConstant.USER_NOT_FOUND);

		if (user.get().getUserType().equals("Admin")) {

			List<ProductsDto> productsDtos = praductAddDto.getProductsDto();

			for (ProductsDto productsDto : productsDtos) {

				BeanUtils.copyProperties(productsDto, product);
				productRepository.save(product);
				responseDto.setStatusCode(ErrorConstant.PRODUCTS_ADDED_SUCC_CODE);
				responseDto.setStatusMessage(ErrorConstant.PRODUCTS_ADDED_SUCC);
			}

		} else {
			responseDto.setStatusCode(ErrorConstant.USER_NOT_ADMIN_CODE);
			responseDto.setStatusMessage(ErrorConstant.USER_NOT_ADMIN);
		}
		return responseDto;

	}

	@Override
	public List<ProductResDto> searchProductByProdName(SearchReqDto searchReqDto, Long id)
			throws ProductException, UserNotFoundException, UserException {

		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException(ErrorConstant.USER_NOT_FOUND);

		if (searchReqDto.getProductName().isEmpty())
			throw new UserException(ErrorConstant.INVALID_INPUT);

		if (user.get().getUserType().equals("priority")) {
			List<Products> prdList = productRepository.findByName(searchReqDto.getProductName());

			if (prdList.isEmpty())
				throw new ProductException(ErrorConstant.NO_RECORD_FOUND);

			List<ProductResDto> productResDto = prdList.stream().map(product -> {
				ProductResDto dto = new ProductResDto();
				BeanUtils.copyProperties(product, dto);
				return dto;
			}).collect(Collectors.toList());
			if (productResDto.isEmpty())
				throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
			else
				return productResDto;

		} else {
			List<Products> prdList = productRepository.findByNameAndType(user.get().getUserType(),
					searchReqDto.getProductName());

			if (prdList.isEmpty())
				throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
			else {
				List<ProductResDto> productsDtos = prdList.stream().map(product -> {
					ProductResDto dto = new ProductResDto();
					BeanUtils.copyProperties(product, dto);
					return dto;
				}).collect(Collectors.toList());
				return productsDtos;
			}

		}

	}

	@Override
	public ResponseDto rateProduct(RateProductDto rateProductDto) throws UserException, RateException {

		Optional<User> user = userRepository.findById(rateProductDto.getUserId());
		Optional<Products> product = productRepository.findById(rateProductDto.getProductId());
		ResponseDto responseDto = null;
		if (!user.isPresent())
			throw new UserException(ErrorConstant.NO_EMAIL_FOUND);
		else if (!product.isPresent()) {
			throw new UserException(ErrorConstant.NO_RECORD_FOUND);
		} else if (user.get().getBuyProduct().isEmpty()) {
			throw new RateException(ErrorConstant.BUY_ERROR);
		} else {
			float rating = product.get().getRating();

			float userRating = rateProductDto.getRating();

			List<PurchaseHistory> purchaseHistories = user.get().getBuyProduct();
			for (PurchaseHistory purchaseHistory : purchaseHistories) {
				purchaseHistory.setRating(userRating);
				purchaseHistoryRepository.save(purchaseHistory);
			}

			float total = (rating + userRating) / 2;

			product.get().setRating(total);
			productRepository.save(product.get());
			responseDto = new ResponseDto();
			responseDto.setStatusMessage(ErrorConstant.RATE_SUCCESS);
			responseDto.setStatusCode(ErrorConstant.RATE_SUCCESS_CODE);

		}

		return responseDto;
	}

}
