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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
   // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Score> users;
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
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //private List<Team> teams;
    @JoinColumn(name = "team_id")
    @ManyToOne(targetEntity = Team.class)
    private Team team;
    @Column(name = "pic_id")
    private String picId;

    @JoinColumn(name = "role_name")
    @OneToOne(targetEntity = UserRoles.class)
    private UserRoles userRole;

    public User() {
    }

    public User(int userId, String firstName, String lastName, String email,
            String telefon, String city, String department, String pin_code, Team team, String picId, UserRoles userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telefon = telefon;
        this.city = city;
        this.department = department;
        this.pin_code = pin_code;
        this.team = team;
        this.picId = picId;
        this.userRole = userRole;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    @Override
    public String toString() {
        return "User data are [ userid= " + userId + " " + "First name= " + firstName + " " + "Last name= " + lastName
                + " " + "email= " + email + " " + " " + "department= " + department
                + " " + "telefon" + telefon + " " + "city" + city + " " + "pin_code" + pin_code + " " + "]";

    }

}
