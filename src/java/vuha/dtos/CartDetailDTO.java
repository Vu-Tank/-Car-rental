/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.dtos;

/**
 *
 * @author Admin
 */
public class CartDetailDTO {
    private String orderDetalID;
    private String orderID;
    private CarDTO car;
    private int quantity;
    private float price;
    private String checkIn;
    private String checkOut;
    private boolean status;
    private FeedbackDTO feedback;
    public CartDetailDTO() {
    }

    public CartDetailDTO(String orderDetalID, String orderID, CarDTO car, int quantity, float price, String checkIn, String checkOut, boolean status) {
        this.orderDetalID = orderDetalID;
        this.orderID = orderID;
        this.car = car;
        this.quantity = quantity;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public CartDetailDTO(String orderDetalID, String orderID, CarDTO car, int quantity, float price, String checkIn, String checkOut, boolean status, FeedbackDTO feedback) {
        this.orderDetalID = orderDetalID;
        this.orderID = orderID;
        this.car = car;
        this.quantity = quantity;
        this.price = price;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.feedback = feedback;
    }

    public FeedbackDTO getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackDTO feedback) {
        this.feedback = feedback;
    }

    public String getOrderDetalID() {
        return orderDetalID;
    }

    public void setOrderDetalID(String orderDetalID) {
        this.orderDetalID = orderDetalID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
}
