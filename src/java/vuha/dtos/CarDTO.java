/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuha.dtos;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CarDTO implements Serializable{
    private String carID;
    private String categoryID;
    private String carName;
    private String img;
    private String color;
    private int year;
    private float price;
    private int quantity;

    public CarDTO() {
    }

    public CarDTO(String carID, String categoryID, String carName, String img, String color, int year, float price, int quantity) {
        this.carID = carID;
        this.categoryID = categoryID;
        this.carName = carName;
        this.img = img;
        this.color = color;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }

    public CarDTO(String carID, String carName, String img) {
        this.carID = carID;
        this.carName = carName;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
