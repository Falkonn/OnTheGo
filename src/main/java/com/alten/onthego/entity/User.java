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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ka3146
 */
@Entity
@Table(name = "\"USER\"")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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
	@Column(name = "team_id")
	private Integer teamId;
	@Column(name ="pic_id")
	private Integer picId;

	public User() {
	}

	public User(String firstName, String lastName, String email,
			String telefon, String city, String department, String pin_code, int teamId) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telefon = telefon;
		this.city = city;
		this.department = department;
		this.pin_code = pin_code;
		this.teamId = teamId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getTeamId(){
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User data are [ id= " + id + " " + "First name= " + firstName + " " + "Last name= " + lastName
				+ " " + "email= " + email + " " + " " + "department= " + department
				+ " " + "telefon" + telefon + " " + "city" + city + " " + "pin_code" + pin_code + " " + "]";

	}

}
