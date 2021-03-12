package com.star.onlineshopping.dto;

import java.util.List;


public class PurchaseHistoryDto {
	
	private long userId;
	private List<UserProdoctDto> userProductDto;
	//for these 2 generate setter nd getter
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<UserProdoctDto> getUserProductDto() {
		return userProductDto;
	}
	public void setUserProductDto(List<UserProdoctDto> userProductDto) {
		this.userProductDto = userProductDto;
	}
	

}
