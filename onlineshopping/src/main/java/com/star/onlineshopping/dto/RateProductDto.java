package com.star.onlineshopping.dto;


public class RateProductDto {
	private long userId;
	private long productId;
	private float rating;
	//for these all generate setter nd getter
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}

}
