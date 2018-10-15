package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name ="states")
public class State implements Serializable{
	
	@Id
	@Column(name="ide_state")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ideState;
	
	@Column(name="name")
	@NotEmpty(message = "Name is a required field")
	@Size(max=45)
	private String name;
	
	@Column(name="description")
	@Size(max=255)
	private String description;

	//Constructors
	public State() {
		super();
	}
	
	public State(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Long getIdeState() {
		return ideState;
	}

	public void setIdeState(Long ideState) {
		this.ideState = ideState;
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
	
	@Override
	public String toString() {
		return "State [ideState=" + ideState + ", name=" + name + ", description=" + description + "]";
	}
}
