package backend;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int customerId;
    private int cylinderId;
    private String paymentMode;
    private String upiId;
    private String transactionId;
    private String address;
    private String contact;
    private String status;
    private Timestamp bookingDate;

    // Additional fields for displaying
    private String customerName;
    private String cylinderCategory;
    private String cylinderWeight;
    private double cylinderPrice;

    public Booking(int bookingId, int customerId, int cylinderId, String paymentMode, String upiId, String transactionId,
                   String address, String contact, String status, Timestamp bookingDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.cylinderId = cylinderId;
        this.paymentMode = paymentMode;
        this.upiId = upiId;
        this.transactionId = transactionId;
        this.address = address;
        this.contact = contact;
        this.status = status;
        this.bookingDate = bookingDate;
    }

    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getCylinderId() { return cylinderId; }
    public String getPaymentMode() { return paymentMode; }
    public String getUpiId() { return upiId; }
    public String getTransactionId() { return transactionId; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }
    public String getStatus() { return status; }
    public Timestamp getBookingDate() { return bookingDate; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerName() { return customerName; }

    public void setCylinderCategory(String cylinderCategory) { this.cylinderCategory = cylinderCategory; }
    public String getCylinderCategory() { return cylinderCategory; }

    public void setCylinderWeight(String cylinderWeight) { this.cylinderWeight = cylinderWeight; }
    public String getCylinderWeight() { return cylinderWeight; }

    public void setCylinderPrice(double cylinderPrice) { this.cylinderPrice = cylinderPrice; }
    public double getCylinderPrice() { return cylinderPrice; }
}
