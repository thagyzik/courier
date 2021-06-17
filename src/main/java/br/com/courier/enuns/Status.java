package br.com.courier.enuns;

public enum Status {

    OPEN("open"),
    DELIVERED("delivered"),
    CANCELLED("cancelled");

    private final String statusOrder;

    Status(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

}
