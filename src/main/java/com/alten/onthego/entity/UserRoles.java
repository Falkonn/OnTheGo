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
//@Table(name = "\"USER\"")
@Table(name = "\"USERROLES\"")
public class UserRoles implements Serializable {
    
    
    private static final long serialVersionUID = 24L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    public long getRoleId() {
        return roleId;
    }
 

    @Column(name = "role_name")
    private String roleName;

    public UserRoles() {
    }

    public UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "User data are [ roleName= " + roleName + " " + "]";

    }

}
