/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.common.GmailEmailSender;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alten.onthego.common.AltenEmailSender;
import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.crud.updatePic;
import com.alten.onthego.entity.Team;
import com.alten.onthego.entity.User;
import com.alten.onthego.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

@RestController
public class UserCont {

    public static String emailstring;
    public static ArrayList<String> emaillists = new ArrayList<String>();
    public static String idstring;
    public static ArrayList<String> idlists = new ArrayList<String>();

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
    public User findUserById(@PathVariable("id") int id) {
        UserInfo userbyid = new UserInfo();
        return userbyid.findUserById(id);
    }

    @RequestMapping(
            value = "/userbypin/{email.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> findPinByEmail(@PathVariable("email") String email) {
        UserInfo userpinbyemail = new UserInfo();
        return (Collection<User>) userpinbyemail.findPinCodebyEmail(email);
    }

    @RequestMapping(
            value = "/teambyuserid/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getUsersByTeamId(@PathVariable("id") int id) {
        UserInfo teamByTeamId = new UserInfo();
        UserInfo usersByTeamId = new UserInfo();
        List<Team> teamList;
        List<User> userList;
        //ArrayList<ArrayList<Object>> teamAndMembers = new ArrayList<ArrayList<Object>>();
        // Find the team by the User id
        teamList = teamByTeamId.findTeamByUserId(id);
        Team team = teamList.get(0);
        // Find the users by the team id
        userList = usersByTeamId.findAllMembersByTeamId(team.getTeamId());

        List<Object> teamAndMembers = new ArrayList<Object>();
        teamAndMembers.add(team.getTeamId());
        teamAndMembers.add(team.getTeamName());
        teamAndMembers.add(userList.size());
        teamAndMembers.add(userList);

        return teamAndMembers;
    }

    @RequestMapping(
            value = "/emailpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEmailAddress(@RequestBody String emailAddress, HttpServletResponse res) throws MessagingException, IllegalBlockSizeException, BadPaddingException {
        UserInfo user = new UserInfo();
        System.out.print(emailAddress);
        Collection<User> foundUsers = user.findUserByEmail(emailAddress);
        Gson gson = new Gson();
        String serializedUsers = gson.toJson(foundUsers);
        if (foundUsers != null && !foundUsers.isEmpty()) {
            user.verfyEmail(true);
            AltenEmailSender es = new AltenEmailSender();
            GmailEmailSender gmailes = new GmailEmailSender();
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

            if (emailAddress.contains("alten")) {
                es.sendEmail("smtp.alten.se", "25", "noreply-destinationlindholmen@alten.se", emailAddress,
                        "Din PIN-kod Destination Lindholmen", "Hej  " + username + "  " + lastName + ","
                        + "<html> <br /><br /> Välkommen till Destination Lindholmen! <br /> Din PIN-kod är: " + PIN_CODE
                        + "<br /> Kopiera koden och snabba dig tillbaka till inloggningssidan för att aktivera din profil!<br /><br />"
                        + "Med vänliga partyhälsningar,<br /> Eventteamet <br />Destination Lindholmen </html>", null);
                //es.sendEmail("smtp.gmail.com", "587", "onthego.alten@gmail.com", "rootrootroot", "khaled.nawasreh@gmail.com","anysub", "hi here is email message", null);
                System.out.println("The email is sent!");
            } else {
                gmailes.sendEmail("smtp.alten.se", "25", "noreply-destinationlindholmen@alten.se", "Lindholmen2015", emailAddress,
                        "Din PIN-kod Destination Lindholmen", "Hej  " + username + "  " + lastName + ","
                        + "<html> <br /><br /> Välkommen till Destination Lindholmen! <br /> Din PIN-kod är: " + PIN_CODE
                        + "<br /> Kopiera koden och snabba dig tillbaka till inloggningssidan för att aktivera din profil!<br /><br />"
                        + "Med vänliga partyhälsningar,<br /> Eventteamet <br />Destination Lindholmen </html>", null);
            }
        } else {
            user.verfyEmail(false);
            System.out.println("There is no email");
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        emailstring = "{\"email\" : \"" + emailAddress + "\"}";
        System.out.println("the found users are " + serializedUsers);
        emaillists.add(emailstring);
        return serializedUsers;
    }

    @RequestMapping(
            value = "/confirmpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserConfirmData(@RequestBody String confirmedUser, HttpServletResponse res) throws MessagingException {

        Gson gson = new Gson();  
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(confirmedUser);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        User confirmedUserObject = gson.fromJson(jsonObject, User.class);
        UserInfo user = new UserInfo();
        User updatedUser = user.updateUser(confirmedUserObject);
        
        String serializedUser = gson.toJson(updatedUser);
        if (updatedUser != null) {
            System.out.println("Found User with this id");
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("Not found User with this id");
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        System.out.println("The confirmed user is " + serializedUser);
        return serializedUser;
    }

    @RequestMapping(
            value = "/pinpath",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean CheckPinCode(@RequestBody String userCredentials, HttpServletResponse ress) throws IllegalBlockSizeException, BadPaddingException {
        UserInfo validateuser = new UserInfo();
        String[] splited = userCredentials.split(" ");
        String email = splited[0];
        String pinCode = splited[1];
        System.out.println("email  " + email + "pincode   " + pinCode);
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

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void getImage(@RequestBody String imagedata, HttpServletResponse response) {
        try {

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(imagedata);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String imageData = jsonObject.get("imageData").getAsString();
            int userId = jsonObject.get("userId").getAsInt();
            // remove data:image/png;base64, and then take rest string
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(imageData);
            ByteArrayInputStream baisData = new ByteArrayInputStream(decodedBytes);

            File outputfile = new File("C:\\Tomcat\\apache-tomcat-8.0.28\\work\\Catalina\\localhost\\onthego\\img\\" + userId + ".png");

            System.out.println("file name is: " + outputfile);
            System.out.println("image datais  " + imageData);
            BufferedImage bfi = ImageIO.read(baisData);
            //we might need to save it in the data base later on
            // File outputfilew = new File("saved.png");
            if (bfi == null) {
                System.out.println("imag is empty");
            }
            ImageIO.write(bfi, "png", outputfile);
            System.out.println("Image file written successfully");
            updatePic up = new updatePic();
            up.addPic(userId+".png", userId);

            // ImageIO.write(bfi, "png", outputfile);
            System.out.println("Image saved");
            bfi.flush();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
