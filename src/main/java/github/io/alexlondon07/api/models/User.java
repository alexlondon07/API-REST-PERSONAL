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
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	@NotEmpty(message = "Name is a required field")
	@Size(max = 60)
	private String name;

	@Column(name = "lastname")
	@NotEmpty(message = "Last name is a required field")
	@Size(max = 60)
	private String lastName;

	@Column(name = "role")
	@Size(max = 10)
	@NotEmpty(message = "Role is a required field")
	private String role;

	@Column(name = "user")
	@NotEmpty(message = "User is a required field")
	@Size(max = 30)
	private String user;

	@Column(name = "pwd")
	@NotEmpty(message = "Password is a required field")
	@Size(max = 100)
	private String pwd;

	// Getter and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	// Constructors
	public User() {
		super();
	}

	public User(long id, String name, String lastName, String role, String user, String pwd) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.role = role;
		this.user = user;
		this.pwd = pwd;
	}

}
