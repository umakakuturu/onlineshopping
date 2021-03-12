package com.star.onlineshopping.entity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHONE_NUMBER", unique = true)
	private String phoneNumber;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CONFIRM_PASSWORD")
	private String confirmPassword;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<PurchaseHistory> buyProduct;
	//for these all generate setter nd getter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<PurchaseHistory> getBuyProduct() {
		return buyProduct;
	}

	public void setBuyProduct(List<PurchaseHistory> buyProduct) {
		this.buyProduct = buyProduct;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
