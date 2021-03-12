package com.star.onlineshopping.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.star.onlineshopping.entity.PurchaseHistory;
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {
	

}