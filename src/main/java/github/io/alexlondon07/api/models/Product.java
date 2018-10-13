package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name ="products")
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8157928487443214003L;

	@Id
	@Column(name="ide_product")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ideProduct;
	
	@Column(name="name")
	@NotEmpty(message = "Name is a required field")
	@Size(max=150)
	private String name;
	
	@Column(name="description")
	@Size(max=300)
	private String description;
	
	@Column(name="cost")
	@NotEmpty(message = "Cost is a required field")
	private String cost;
	
	@Column(name="price")
	@NotEmpty(message = "Price is a required field")
	private String price;
	
	@Column(name="image")
	private String image;
	
	@Column(name="enable")
	@Size(max=1)
	private String enable;
	

	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ide_category")
	private Category category;
	
	//Constructors
	public Product() {
		super();
	}

	public Product(String name, String description, String cost, String price, String enable) {
		super();
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.price = price;
		this.enable = enable;
	}

	//Getters and Setters	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Long getIdeProduct() {
		return ideProduct;
	}

	public void setIdeProduct(Long ideProduct) {
		this.ideProduct = ideProduct;
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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [ideProduct=" + ideProduct + ", name=" + name + ", description=" + description + ", cost="
				+ cost + ", price=" + price + ", enable=" + enable + ", category=" + category + "]";
	}	
	
}
