package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="clients")
public class Client implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1682943283826480346L;

	@Id
	@Column(name="ide_client")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idClient;
	
	@Column(name="name")
	private String name;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="identification")
	private String identification;
	
	@Column(name="cellphone")
	private String cellphone;
	
	@Column(name="city")
	private String city;
	
	@Column(name="enable")
	private char enable;
	
	
	//Constructors
	public Client() {
		super();
	}
	
	public Client(String name, String lastName, String cellphone, String city, char enable) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.city = city;
		this.enable = enable;
	}

	//Getter and Setters
	public Long getIdClient() {
		return idClient;
	}	
	public void setIdClient(Long idCliente) {
		this.idClient = idCliente;
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
	public char getEnable() {
		return enable;
	}
	public void setEnable(char enable) {
		this.enable = enable;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
}
