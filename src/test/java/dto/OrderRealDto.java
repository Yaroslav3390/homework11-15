package dto;

public class OrderRealDto {

    String customerName;
    String customerPhone;
    String comment;
    String status;
    Integer courierId;
    int id;



    public OrderRealDto(String customerName, String customerPhone, String comment, int courierId, int id) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.status = "OPEN";
        this.courierId = courierId;
        this.id = id;
    }

    public OrderRealDto() {
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public int getId() {
        return id;
    }
}
