package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="category")
public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="ide_category")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ideCategory;
	
	@Column(name="name")
	@NotNull
	@Size(max=100)
	private String name;
	
	@Column(name="description")
	@Size(max=200)
	private String description;

	@Column(name="enable")
	@Size(max=1)
	private String enable;

	
	//Constructors
	public Category() {
		super();
	}
	
	public Category(String name, String description, String enable) {
		super();
		this.name = name;
		this.description = description;
		this.enable = enable;
	}

	//Getters and Setters
	public long getIdCategory() {
		return ideCategory;
	}

	public void setIdCategory(long idCategory) {
		this.ideCategory = idCategory;
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

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "Category [ideCategory=" + ideCategory + ", name=" + name + ", description=" + description + ", enable="
				+ enable + "]";
	}
	
}
