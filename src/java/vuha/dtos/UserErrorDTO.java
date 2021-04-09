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
public class UserErrorDTO {
    private String emailError;
    private String phoneError;
    private String nameError;
    private String addressError;
    private String passwordError;
    private String confirmError;

    public UserErrorDTO() {
    }

    public UserErrorDTO(String emailError, String phoneError, String nameError, String addressError, String passwordError, String confirmError) {
        this.emailError = emailError;
        this.phoneError = phoneError;
        this.nameError = nameError;
        this.addressError = addressError;
        this.passwordError = passwordError;
        this.confirmError = confirmError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    
    
}
