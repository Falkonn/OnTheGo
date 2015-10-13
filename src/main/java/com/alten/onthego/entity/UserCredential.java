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
public class UserCredential {

    private String emailAddress;
    private String pinCode;

    public UserCredential(String emailAddress, String pinCode) {
        this.emailAddress = emailAddress;
        this.pinCode = pinCode;
    }

    public void setEmailAddress(String email) {
        emailAddress = email;
    }
    
    
    public String getEmailAddress(){
        return emailAddress;
    }
    
    public String getPinCode(){
        return pinCode;
    }

}
