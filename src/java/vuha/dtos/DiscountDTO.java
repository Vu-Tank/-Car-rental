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
public class DiscountDTO {
    private String DiscountCode;
    private float value;

    public DiscountDTO(String DiscountCode, float value) {
        this.DiscountCode = DiscountCode;
        this.value = value;
    }

    public String getDiscountCode() {
        return DiscountCode;
    }

    public void setDiscountCode(String DiscountCode) {
        this.DiscountCode = DiscountCode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
}
