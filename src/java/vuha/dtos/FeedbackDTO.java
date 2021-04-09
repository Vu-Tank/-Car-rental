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
public class FeedbackDTO {
    private String feedbackID;
    private String orderDetailID;
    private int value;

    public FeedbackDTO() {
    }

    
    public FeedbackDTO(String feedbackID, String orderDetailID, int value) {
        this.feedbackID = feedbackID;
        this.orderDetailID = orderDetailID;
        this.value = value;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
