package com.star.onlineshopping.service;

import com.star.onlineshopping.dto.PurchaseHistoryDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;

public interface PurchaseHistoryService {
	public ResponseDto buyProduct(PurchaseHistoryDto purchaseHistoryDto) throws UserException, ProductException;

}
