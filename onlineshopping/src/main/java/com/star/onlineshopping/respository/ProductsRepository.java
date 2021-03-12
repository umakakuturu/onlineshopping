package com.star.onlineshopping.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.star.onlineshopping.entity.Products;

/**
 * @author User1
 *
 */
@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

	public List<Products> findByType(String type);

	
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:name%")
	public List<Products> findByName( @Param("name")String name);


    @Query("SELECT p FROM Products p WHERE p.name LIKE %:name% And p.type=:type")
	public List<Products> findByNameAndType(String type, @Param("name")String name);

}
