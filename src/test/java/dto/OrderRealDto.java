package dto;

public class OrderRealDto {

    String customerName;
    String customerPhone;
    String comment;
    String status;

    public OrderRealDto(String customerName, String customerPhone, String comment) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.status = "OPEN";

    }
}
