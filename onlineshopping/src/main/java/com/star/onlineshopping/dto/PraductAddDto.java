/**
 * 
 */
package com.star.onlineshopping.dto;

import java.util.List;

public class PraductAddDto {

	private Long userId;

	private List<ProductsDto> productsDto;

//for these 2 generate setter nd getter
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<ProductsDto> getProductsDto() {
		return productsDto;
	}

	public void setProductsDto(List<ProductsDto> productsDto) {
		this.productsDto = productsDto;
	}

}
