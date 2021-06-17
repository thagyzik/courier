package br.com.courier.model;

import br.com.courier.enuns.Status;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long idOrder;

	private Double weight;

	private Double cost;

	private LocalDate localDate;

	private LocalDate delivered;

	private Status status;

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "sender_details")
	private Detail senderDetails;

	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "receiver_details")
	private Detail receiverDetails;

	public Order() {
	}

	public Order(Double weight, Double cost, LocalDate localDate, Detail senderDetails, Detail receiverDetails, Status status, LocalDate delivered) {
		this.weight = weight;
		this.cost = cost;
		this.senderDetails = senderDetails;
		this.receiverDetails = receiverDetails;
		this.localDate = localDate;
		this.status = status;
		this.delivered = delivered;
	}

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Detail getSenderDetails() {
		return senderDetails;
	}

	public void setSenderDetails(Detail senderDetails) {
		this.senderDetails = senderDetails;
	}

	public Detail getReceiverDetails() {
		return receiverDetails;
	}

	public void setReceiverDetails(Detail receiverDetails) {
		this.receiverDetails = receiverDetails;
	}

	public LocalDate getDelivered() {
		return delivered;
	}

	public void setDelivered(LocalDate delivered) {
		this.delivered = delivered;
	}

	@Override
	public String toString() {
		return "{" +
				"idOrder=" + idOrder +
				", weight=" + weight +
				", cost=" + cost +
				", localDate=" + localDate +
				", delivered=" + delivered +
				", status=" + status +
				", senderDetails=" + senderDetails +
				", receiverDetails=" + receiverDetails +
				'}';
	}
}
