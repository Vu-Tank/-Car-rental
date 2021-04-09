/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartDTO implements Serializable{

    private String orderID;
    private String email;
    private float price;
    private String discountCode;
    private boolean status;
    private String bookingDate;
    private Map<String, CartDetailDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String orderID, String email, float price, String discountCode, boolean status, Map<String, CartDetailDTO> cart) {
        this.orderID = orderID;
        this.email = email;
        this.price = price;
        this.discountCode = discountCode;
        this.status = status;
        this.cart = cart;
    }

    public CartDTO(String orderID, String email, float price, String discountCode, boolean status, String bookingDate, Map<String, CartDetailDTO> cart) {
        this.orderID = orderID;
        this.email = email;
        this.price = price;
        this.discountCode = discountCode;
        this.status = status;
        this.bookingDate = bookingDate;
        this.cart = cart;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Map<String, CartDetailDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, CartDetailDTO> cart) {
        this.cart = cart;
    }

    public void add(CartDetailDTO car) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        boolean check=false;
        for (CartDetailDTO value : this.cart.values()) {
            if (value.getCar().getCarID().equals(car.getCar().getCarID()) && value.getCheckIn().equals(car.getCheckIn()) && value.getCheckOut().equals(car.getCheckOut())) {
                int quantity = value.getQuantity();
                value.setQuantity(quantity + 1);
                Date retalDate = Date.valueOf(value.getCheckIn());
                Date returnDate = Date.valueOf(value.getCheckOut());
                long totalDate = (returnDate.getTime() - retalDate.getTime()) / (1000 * 60 * 60 * 24);
                value.setPrice(value.getCar().getPrice()*totalDate*value.getQuantity());
                cart.replace(car.getOrderDetalID(), value);
                check=true;
            }
        }
        if(!check)
            cart.put(car.getOrderDetalID(), car);
    }

    public void update(CartDetailDTO car) {
        if (this.cart != null) {
            if (this.cart.containsKey(car.getOrderDetalID())) {
                this.cart.replace(car.getOrderDetalID(), car);
            }
        }
    }

    public void delete(String orderDetailID) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(orderDetailID)) {
            this.cart.remove(orderDetailID);
        }
    }
}
