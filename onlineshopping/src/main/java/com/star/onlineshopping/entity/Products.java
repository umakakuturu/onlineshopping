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
@Table(name = "Products", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Products implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "QUANTITY")
	private int quantity;

	@Column(name = "Rating")
	private float rating;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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
