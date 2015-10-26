/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

import com.alten.onthego.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ka3146
 */
public class updatePic {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();
    boolean picAdded = false;

    public boolean addPic(String path, int userId) {
        try {
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setPicId(path);
                EntityTransaction entityTx = em.getTransaction();
                entityTx.begin();
                em.merge(user);
                entityTx.commit();
                System.out.println();
                System.out.println("The pic has been added!");
                picAdded = true;
            } else {
                System.out.println("Error in adding the pic!");
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            em.close();
        }
        return picAdded;
    }
}
