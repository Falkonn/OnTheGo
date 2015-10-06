/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
            User user = em.find(User.class, 1L);;
            if (user != null) {
                System.out.println(user);
                userinfo.add(user);
            } else {
                System.out.println("Employee table is empty!");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            em.close();
        }
        return new ArrayList<>(UserInfo.userinfo);
    }

    public Collection<User> findAllUsers() {
        Query query = em.createQuery("SELECT e FROM User e");
        return (Collection<User>) query.getResultList();
    }

    public Collection<User> findUserByEmail(String email) {
        Query query = em.createQuery("select e from User e where e.email =" + "\""+email+"\"");
        return(Collection<User>) query.getResultList();
    }

    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    public void removeUser(long id) {
        User removeuser = findUserById(id);
        if (removeuser != null) {
            em.remove(removeuser);
        }
    }
}
