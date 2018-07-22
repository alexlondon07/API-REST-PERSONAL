package github.io.alexlondon07.api.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private int ideProduct;
	
	@Column(name="name")
	@NotNull
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="cost")
	@NotNull
	private double cost;
	
	@Column(name="price")
	@NotNull
	private double price;
	
	@Column(name="enable")
	@Size(max=1)
	private char enable;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ide_category")
	private Category category;
	
	//Constructors
	public Product() {
		super();
	}

	public Product(String name, String description, double cost, double price, char enable) {
		super();
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.price = price;
		this.enable = enable;
	}

	//Getters and Setters
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
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
