/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.entity.Team;
import com.alten.onthego.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ka3146
 */
public class UserInfo {

    private static List<User> userinfo = new ArrayList<User>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();

    public List<User> getUsers() {

        try {
            User user = em.find(User.class, 1L);
            if (user != null) {
                System.out.println(user);
                userinfo.add(user);
            } else {
                System.out.println("User table is empty!");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            em.close();
        }
        return new ArrayList<>(UserInfo.userinfo);
    }

    public Collection<User> findAllUsers() {
        Query allusersquery = em.createQuery("SELECT e FROM User e");
        return (Collection<User>) allusersquery.getResultList();
    }

    public Collection<User> findUserByEmail(String email) {
        Query finduserbyemailquery = em.createQuery("select e from User e where e.email =" + "\"" + email + "\"");
        return (Collection<User>) finduserbyemailquery.getResultList();

    }

    public boolean verfyEmail(boolean flage) {
        return flage;
    }

    public Collection<User> findPinCodebyEmail(String email) {
        Query findpincodebyemailquery = em.createQuery("select e.pin_code from User e where e.email =" + "\"" + email + "\"");
        return (Collection<User>) findpincodebyemailquery.getResultList();
    }

    public Collection<User> findUserFirstNamebyEmail(String email) {
        Query findUserFirstNamebyEmailQ = em.createQuery("select e.firstName from User e where e.email =" + "\"" + email + "\"");
        return (Collection<User>) findUserFirstNamebyEmailQ.getResultList();
    }

    public Collection<User> findUserLastNamebyEmail(String email) {
        Query findUserLastNamebyEmailQ = em.createQuery("select e.lastName from User e where e.email =" + "\"" + email + "\"");
        return (Collection<User>) findUserLastNamebyEmailQ.getResultList();
    }

    public List<Integer> findUsersByTeamId(int teamid) {
        Query findUsersByteamIdQ = em.createQuery("select e.userId from User e where e.team.teamId  =" + teamid);
        return (List<Integer>) findUsersByteamIdQ.getResultList();
    }
    
    public List<User> findAllMembersByTeamId(int teamid) {
        Query findAllMembersByTeamIdQ = em.createQuery("select e from User e where e.team.teamId  =" + teamid);
        return (List<User>) findAllMembersByTeamIdQ.getResultList();
    }
    
    public List<Team> findTeamByUserId(int userId) {
        Query findTeamByUserId = em.createQuery("select e.team from User e where e.userId  =" + userId);
        return (List<Team>) findTeamByUserId.getResultList();
    }

    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    public boolean validUser(String email, String pinCode) throws IllegalBlockSizeException, BadPaddingException {
        boolean valid = false;
        if ((email != null) && (email.length() > 0)) {
            Collection<User> pass = findPinCodebyEmail(email);
            Iterator ite = pass.iterator();
            Object userpincode = ite.next();
            // pinCode = pinCode.replaceAll("\\D+","");
            PassEncryption pe = new PassEncryption();
            String userpincodedec = pe.DecryptText(userpincode.toString());
            valid = userpincodedec.contains(pinCode);
        }
        return valid;
    }

    public void removeUser(long id) {
        User removeuser = findUserById(id);
        if (removeuser != null) {
            em.remove(removeuser);
        }
    }

    public List<Integer> getTeamIdbyUserId(int userId) {
        Query scoresquery = em.createQuery("SELECT u.team.teamId FROM User u where u.userId=" + userId);
        return (List<Integer>) scoresquery.getResultList();
    }

    public List<Integer> getUserByTeamIdAndTaskId(int teamId, long taskId) {
        Query userbyteamandtaskidquery = em.createQuery("SELECT u.user.userId FROM Score u where u.teamId =" + teamId + " AND u.task.taskId =" + taskId);
        return (List<Integer>) userbyteamandtaskidquery.getResultList();
    }

    public User updateUser(User confUser) {
        User updateUser = findUserById(confUser.getUserId());
        if (updateUser != null) {
            em.getTransaction().begin();
            updateUser.setFirstName(confUser.getFirstName());
            updateUser.setTelefon(confUser.getTelefon());
            updateUser.setEmail(confUser.getEmail());
            em.getTransaction().commit();
        }
        return updateUser;
    }
}
