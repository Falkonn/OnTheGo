/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.common.EmailSending;
import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.model.UserInfo;
import com.alten.onthego.entity.User;
import com.google.gson.Gson;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(
            value = "/userbypin/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findPinByEmail(@PathVariable("email") String email) {
        UserInfo userpinbyemail = new UserInfo();
        return (Collection<User>) userpinbyemail.findPinCodebyEmail(email);
    }

    @RequestMapping(
            value = "/emailpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEmailAddress(@RequestBody String emailAddress, HttpServletResponse res) throws MessagingException, IllegalBlockSizeException, BadPaddingException {
        UserInfo user = new UserInfo();
        Collection<User> foundUsers = user.findUserByEmail(emailAddress);
        Gson gson = new Gson();
        String serializedUsers = gson.toJson(foundUsers);
        if (foundUsers != null && !foundUsers.isEmpty()) {
            user.verfyEmail(true);
            EmailSending es = new EmailSending();
            System.out.println("There is an email");
            res.setStatus(HttpServletResponse.SC_OK);
            Collection<User> name = user.findUserFirstNamebyEmail(emailAddress);
            Iterator iter = name.iterator();
            Object username = iter.next();
            Collection<User> lastname = user.findUserLastNamebyEmail(emailAddress);
            Iterator iiter = lastname.iterator();
            Object lastName = iiter.next();
            Collection<User> pinCode = user.findPinCodebyEmail(emailAddress);
            Iterator ite = pinCode.iterator();
            Object userpincode = ite.next();
            PassEncryption pe = new PassEncryption();
            String PIN_CODE = pe.DecryptText((String) userpincode);
            es.sendEmail("smtp.gmail.com", "587", "onthego.alten@gmail.com", "rootrootroot", emailAddress,
                    "Din PIN-kod Destination Lindholmen", "Hej  " + username + "  " + lastName + ","
                    + "<html> <br /><br /> Välkommen till Destination Lindholmen! <br /> Din PIN-kod är: " + PIN_CODE
                    + "<br /> Kopiera koden och snabba dig tillbaka till inloggningssidan för att aktivera din profil!<br /><br />"
                    + "Med vänliga partyhälsningar,<br /> Eventteamet <br />Destination Lindholmen </html>", null);
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
    public boolean CheckPinCode(@RequestBody String email, String pinCode, HttpServletResponse ress) throws IllegalBlockSizeException, BadPaddingException {
        UserInfo validateuser = new UserInfo();
        boolean verifyuser = validateuser.validUser(email, pinCode);
        if (verifyuser) {
            System.out.println("User is verfied");
            ress.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("User is not verfied");
            ress.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return verifyuser;
    }

    /*//This code is just for testing
     public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, BadPaddingException {
     UserInfo userinfo = new UserInfo();
     PassEncryption pe = new PassEncryption();;
     Collection<User> pinCode = userinfo.findPinCodebyEmail("khaled.nawasreh@gmail.com");
     Iterator ite = pinCode.iterator();
     Object userpincode = ite.next();
     userpincode = pe.DecryptText((String) userpincode);
     System.out.println("result" + userpincode);
     }*/
}
