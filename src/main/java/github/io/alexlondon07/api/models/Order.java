package github.io.alexlondon07.api.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name ="orders")
public class Order implements Serializable {

	@Id
	@Column(name="ide_order")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ideOrder;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ide_client")
	private Client client;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "ide_state")
	private State state;
    
	@Column(name="date_order")
	@NotEmpty(message = "Date Order is a required field")
	private String dateOrder;
	
	@Column(name="date_payment")
	@NotEmpty(message = "Date Payment is a required field")
	private String datePayment;
	
	@Column(name="date_of_delivery")
	@NotEmpty(message = "Date of Delivery is a required field")
	private String dateOfDelivery;
	
	@Column(name="observations")
	@Size(max=255)
	private String observations;

	public Order() {
		super();
	}

	public Order(Client client, State state, String dateOrder, String datePayment, String dateOfDelivery,
			String observations) {
		super();
		this.client = client;
		this.state = state;
		this.dateOrder = dateOrder;
		this.datePayment = datePayment;
		this.dateOfDelivery = dateOfDelivery;
		this.observations = observations;
	}
	
	public Long getIdeOrder() {
		return ideOrder;
	}

	public void setIdeOrder(Long ideOrder) {
		this.ideOrder = ideOrder;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}

	public String getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(String datePayment) {
		this.datePayment = datePayment;
	}

	public String getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	@Override
	public String toString() {
		return "Order [ideOrder=" + ideOrder + ", client=" + client + ", state=" + state + ", dateOrder=" + dateOrder
				+ ", datePayment=" + datePayment + ", dateOfDelivery=" + dateOfDelivery + ", observations="
				+ observations + "]";
	}
}
