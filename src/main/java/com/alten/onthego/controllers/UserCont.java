/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.common.EmailSending;
import com.alten.onthego.model.UserInfo;
import com.alten.onthego.entity.User;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.persistence.sessions.serializers.JSONSerializer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCont {

    public static String emailstring;
    public static ArrayList<String> emaillists = new ArrayList<String>();
    public static String idstring;
    public static ArrayList<String> idlists = new ArrayList<String>();

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
            value = "/userbyemail/{email.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findUserByEmail(@PathVariable("email.+") String email) {
        UserInfo userbyemail = new UserInfo();
        System.out.print("from GET" + email + "\n");
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

    @RequestMapping(
            value = "/emailpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEmailAddress(@RequestBody String emailAddress, HttpServletResponse res) throws MessagingException {
        UserInfo user = new UserInfo();
        Collection<User> foundUsers = user.findUserByEmail(emailAddress);
        JSONSerializer serializer = new JSONSerializer();
        Gson gson = new Gson();
        String serializedUsers = gson.toJson(foundUsers);
        if (foundUsers != null && !foundUsers.isEmpty()) {
            user.verfyEmail(true);
            EmailSending es = new EmailSending();
            System.out.println("There is an email");
               res.setStatus(HttpServletResponse.SC_OK);
            //es.sendEmail("smtp.gmail.com", "587", "onthego.alten@gmail.com", "rootrootroot", "khaled.nawasreh@gmail.com","anysub", "hi here is email message", null);
            System.out.println("The email is sent!");
        } else {
            user.verfyEmail(false);
            System.out.println("There is no email");
            
               res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        emailstring = "{\"email\" : \"" + emailAddress + "\"}";
        System.out.println("the found users are " + serializedUsers);
        emaillists.add(emailstring);
        return serializedUsers.toString();
    }
    
    @RequestMapping(
            value = "/pinpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserId(@RequestBody String userId, HttpServletResponse res) throws MessagingException {
        UserInfo user = new UserInfo();
        User foundUser = user.findUserById(Long.parseLong(userId,10));
        JSONSerializer serializer = new JSONSerializer();
        Gson gson = new Gson();
        String serializedUser = gson.toJson(foundUser);
        System.out.println(userId);
        if (foundUser != null) {
            System.out.println("Found User with this id");
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("Not found User with this id");
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        idstring = "{\"id\" : \"" + userId + "\"}";
        System.out.println("the found user is " + serializedUser);
        idlists.add(idstring);
        return serializedUser.toString();
    }
    
    @RequestMapping(
            value = "/confirmpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserConfirmData(@RequestBody User confirmedUser, HttpServletResponse res) throws MessagingException {
        UserInfo user = new UserInfo();   
        User updatedUser = user.updateUser(confirmedUser);
        JSONSerializer serializer = new JSONSerializer();
        Gson gson = new Gson();
        String serializedUser = gson.toJson(updatedUser);
        if (updatedUser != null) {
            System.out.println("Found User with this id");
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("Not found User with this id");
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        System.out.println("The confirmed user is " + serializedUser);

        return serializedUser.toString();
    }

}
