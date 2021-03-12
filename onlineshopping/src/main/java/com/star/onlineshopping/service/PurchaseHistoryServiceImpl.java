package com.star.onlineshopping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.onlineshopping.dto.PurchaseHistoryDto;
import com.star.onlineshopping.dto.ResponseDto;
import com.star.onlineshopping.dto.UserProdoctDto;
import com.star.onlineshopping.entity.Products;
import com.star.onlineshopping.entity.PurchaseHistory;
import com.star.onlineshopping.entity.User;
import com.star.onlineshopping.exception.ProductException;
import com.star.onlineshopping.exception.UserException;
import com.star.onlineshopping.respository.ProductsRepository;
import com.star.onlineshopping.respository.PurchaseHistoryRepository;
import com.star.onlineshopping.respository.UserRepository;
import com.star.onlineshopping.utility.ErrorConstant;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductsRepository productsRepository;
	@Autowired
	PurchaseHistoryRepository purchaseHistoryRepository;

	@Override
	public ResponseDto buyProduct(PurchaseHistoryDto purchaseHistoryDto) throws UserException, ProductException {

		Optional<User> user = userRepository.findById(purchaseHistoryDto.getUserId());
		List<Products> productList = new ArrayList<>();
		int userDtoSize = purchaseHistoryDto.getUserProductDto().size();

		if (!user.isPresent())
			throw new UserException(ErrorConstant.NO_EMAIL_FOUND);
		else {
			String userType = user.get().getUserType();
			for (int index = 0; index < userDtoSize; index++) {
				UserProdoctDto userProdoctDto = purchaseHistoryDto.getUserProductDto().get(index);
				long productId = userProdoctDto.getProductId();
				int quantity = userProdoctDto.getQuantity();
				Optional<Products> product = productsRepository.findById(productId);

				if (user.get().getUserType().equals("priority")) {
					if (!product.isPresent()) {
						throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
					} else if (product.get().getQuantity() < quantity) {
						throw new ProductException(ErrorConstant.INSUFFICIANT_ITEM);
					} else {
						productList.add(product.get());
					}

				} else if (!product.isPresent() || !product.get().getType().equals(userType)) {
					throw new ProductException(ErrorConstant.NO_RECORD_FOUND);
				} else if (product.get().getQuantity() < quantity) {
					throw new ProductException(ErrorConstant.INSUFFICIANT_ITEM);
				} else {
					productList.add(product.get());

				}
			}
		}

		for (int index = 0; index < userDtoSize; index++) {
			PurchaseHistory purchaseHistory = new PurchaseHistory();
			UserProdoctDto userProdoctDto = purchaseHistoryDto.getUserProductDto().get(index);
			int quantity = userProdoctDto.getQuantity();
			Products products = productList.get(index);
			products.setQuantity(products.getQuantity() - quantity);

			purchaseHistory.setQuantity(quantity);
			purchaseHistory.setDate(new Date());
			purchaseHistory.setPrice((float) (products.getPrice() * quantity));
			purchaseHistory.setUser(user.get());
			purchaseHistory.setProducts(products);

			productsRepository.save(products);
			purchaseHistoryRepository.save(purchaseHistory);

		}
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatusMessage(ErrorConstant.ORDER_SUCCESS);
		responseDto.setStatusCode(ErrorConstant.NO_RECORD_FOUND_CODE);
		return responseDto;
	}

}
