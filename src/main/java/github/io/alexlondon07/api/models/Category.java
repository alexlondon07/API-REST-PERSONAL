package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category implements Serializable{

	@Id
	@Column(name ="ide_category")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCategory;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;

	@Column(name="enable")
	private char enable;

	
	//Constructors
	public Category() {
		super();
	}
	
	public Category(String name, String description, char enable) {
		super();
		this.name = name;
		this.description = description;
		this.enable = enable;
	}

	//Getters and Setters
	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
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

	public char getEnable() {
		return enable;
	}

	public void setEnable(char enable) {
		this.enable = enable;
	}
}
