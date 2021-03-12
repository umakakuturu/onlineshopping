
package com.star.onlineshopping.service;

import java.util.List;

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

public interface ProductsService {

	List<ProductsDto> getProductByEmail(String email) throws UserException, ProductException;

	public ResponseDto addProduct(PraductAddDto praductAddDto) throws UserNotFoundException;

	public List<ProductResDto> searchProductByProdName(SearchReqDto searchReqDto, Long id)
			throws ProductException, UserNotFoundException, UserException;

	public ResponseDto rateProduct(RateProductDto rateProductDto) throws UserException, RateException;

}
