package com.star.onlineshopping.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.star.onlineshopping.entity.User;

/**
 * @author User1
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

	public User findByEmailAndPhoneNumber(String email, String phoneNumber);

	public Optional<User> findById(Long id);

}
