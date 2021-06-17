package br.com.courier.dto;

import br.com.courier.enuns.Status;
import br.com.courier.model.Detail;
import br.com.courier.model.Order;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewOrderDto {

	private static DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private String idOrder;

	@Min(value = 1, message = "Invalid weight")
	@Size(min = 1, max = 30, message = "Invalid weight")
	private String weight;

	@Min(value = 1, message = "Invalid cost")
	@Size(min = 1, max = 5, message = "Invalid cost")
	private String cost;

	private Status status;

	@Valid
	private NewDetailDto senderDetails;

	@Valid
	private NewDetailDto receiverDetails;

	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Order date is required")
	private String localDate;

	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "correct format dd/MM/yyyy")
	private String delivered;

	public NewOrderDto(Order order) {
		this.idOrder = String.valueOf(order.getIdOrder());
		this.cost = String.valueOf(order.getCost());
		this.weight = String.valueOf(order.getWeight());
		this.receiverDetails = new NewDetailDto(order.getReceiverDetails());
		this.senderDetails = new NewDetailDto(order.getSenderDetails());
		this.status = order.getStatus();
		this.localDate = order.getLocalDate().format(ofPattern);
		this.delivered = order.getDelivered().format(ofPattern);
	}

	public NewOrderDto() {
	}

	public static DateTimeFormatter getOfPattern() {
		return ofPattern;
	}

	public static void setOfPattern(DateTimeFormatter ofPattern) {
		NewOrderDto.ofPattern = ofPattern;
	}

	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public NewDetailDto getSenderDetails() {
		return senderDetails;
	}

	public void setSenderDetails(NewDetailDto senderDetails) {
		this.senderDetails = senderDetails;
	}

	public NewDetailDto getReceiverDetails() {
		return receiverDetails;
	}

	public void setReceiverDetails(NewDetailDto receiverDetails) {
		this.receiverDetails = receiverDetails;
	}

	public String getLocalDate() {
		return localDate;
	}

	public void setLocalDate(String localDate) {
		this.localDate = localDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public Order toOrder() {
		LocalDate dateOrder = null;
		LocalDate dateDelivered = null;
		Detail newDetailSender = null;
		Detail newDetailReceiver = null;
		Double weightDouble = null;
		Double costDouble = null;

		if (localDate != null) {

			dateOrder = LocalDate.parse(this.localDate, ofPattern);

		}

		if (delivered != null) {

			dateDelivered = LocalDate.parse(this.delivered, ofPattern);

		}

		if (senderDetails != null && receiverDetails != null){

			newDetailSender = senderDetails.toDetail();
			newDetailReceiver = receiverDetails.toDetail();

		}

		if (weight != null){

			weightDouble = Double.parseDouble(weight);

		}

		if (cost != null){

			costDouble = Double.parseDouble(cost);

		}

		Order order = new Order(weightDouble, costDouble, dateOrder, newDetailSender, newDetailReceiver, status, dateDelivered);
		if(idOrder.equals("")) {
			order.setIdOrder(null);
		} else {
			order.setIdOrder(Long.valueOf(idOrder));
		}
		return order;
	}

}
