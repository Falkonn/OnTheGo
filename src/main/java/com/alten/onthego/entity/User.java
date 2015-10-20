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
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ka3146
 */
@Entity
//@Table(name = "\"USER\"")
@Table(name = "\"USER\"")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Score> users;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String telefon;
    @Column(name = "city")
    private String city;
    @Column(name = "department")
    private String department;
    @Column(name = "pincode")
    private String pin_code;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Team> teams;
    @Column(name = "team_id")
    private Integer teamId;
    @Column(name = "pic_id")
    private Integer picId;
    @Column(name = "pic_url")
    private String picUrl;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email,
            String telefon, String city, String department, String pin_code, int teamId, int picId, String picUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telefon = telefon;
        this.city = city;
        this.department = department;
        this.pin_code = pin_code;
        this.teamId = teamId;
        this.picId = picId;
        this.picUrl = picUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getCity() {
        return city;
    }

    public String getPinCode() {
        return pin_code;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPinCode(String pin_code) {
        this.pin_code = pin_code;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "User data are [ userid= " + userId + " " + "First name= " + firstName + " " + "Last name= " + lastName
                + " " + "email= " + email + " " + " " + "department= " + department
                + " " + "telefon" + telefon + " " + "city" + city + " " + "pin_code" + pin_code + " " + "]";

    }

}
