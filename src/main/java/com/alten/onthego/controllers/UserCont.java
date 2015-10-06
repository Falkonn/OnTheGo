/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.model.UserInfo;
import com.alten.onthego.entity.User;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCont {

    public static void main(String args[]) {
        UserInfo us = new UserInfo();
        System.out.println("Here we find all the users....." + us.findUserByEmail("sara.nilsson@alten.com"));
    }

    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String greeting() {
        return "test";
    }

    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<User> getUsers() {
        UserInfo user = new UserInfo();
        return new ArrayList<User>(user.getUsers());
    }

    @RequestMapping(
            value = "/allusers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> getAllUsers() {
        UserInfo allusers = new UserInfo();
        return (Collection<User>) allusers.findAllUsers();
    }

    @RequestMapping(
            value = "/userbyemail/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findUserByEmail(@PathVariable("email") String email) {
        UserInfo userbyemail = new UserInfo();
        return (Collection<User>) userbyemail.findUserByEmail(email);
    }

    @RequestMapping(
            value = "/userbyid/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUserById(@PathVariable("id") long id) {
        UserInfo userbyid = new UserInfo();
        return userbyid.findUserById(id);
    }
}
