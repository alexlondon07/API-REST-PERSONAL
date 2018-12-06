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
@Table(name="clients")
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1682943283826480346L;

	@Id
	@Column(name="ide_client")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ideClient;
	
	@Column(name="name")
	@NotEmpty(message = "Name is a required field")
	@Size(max=90)
	private String name;
	
	@Column(name="last_name")
	@NotEmpty(message = "Last name is a required field")
	@Size(max=90)
	private String lastName;
	
	@Column(name="identification")
	@Size(max=10)
	private String identification;
	
	@Column(name="cellphone")
	@NotEmpty(message = "Cellphone is a required field")
	@Size(max=10)
	private String cellphone;
	
	@Column(name="city")
	@NotEmpty(message = "City is a required field")
	@Size(max=45)
	private String city;
	
	@Column(name="address")
	@NotEmpty(message = "Address is a required field")
	@Size(max=150)
	private String address;
	
	@Column(name="enable")
	@Size(max=1)
	private String enable;
	
	//Constructors
	public Client() {
		super();
	}
	
	public Client(String name, String lastName, String identification, String cellphone, String city, String address,
			String enable) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.identification = identification;
		this.cellphone = cellphone;
		this.city = city;
		this.address = address;
		this.enable = enable;
	}

	//Getter and Setters
	public long getIdClient() {
		return ideClient;
	}	
	public void setIdClient(long idCliente) {
		this.ideClient = idCliente;
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
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public long getIdeClient() {
		return ideClient;
	}

	public void setIdeClient(long ideClient) {
		this.ideClient = ideClient;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [ideClient=" + ideClient + ", name=" + name + ", lastName=" + lastName + ", identification="
				+ identification + ", cellphone=" + cellphone + ", city=" + city + ", address=" + address + ", enable="
				+ enable + "]";
	}
}
