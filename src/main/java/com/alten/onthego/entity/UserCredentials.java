/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.entity;

/**
 *
 * @author ka3146
 */
public class UserCredentials {

    public String emailAddress;
    public String pinCode;

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPinCode() {
        return pinCode;
    }
}
