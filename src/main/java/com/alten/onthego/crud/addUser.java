/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ka3146
 */
public class addUser {
//the main class here just for testing purpose
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();
        try {
            User user = new User(19,"dana", "saara", "dasdas.com", "3232", "goteborg", "IT", "WFNbUN7gIoU=", 11, "picid");
            EntityTransaction entityTx = em.getTransaction();
            entityTx.begin();
            em.persist(user);
            entityTx.commit();
            System.out.println("The row has be successfully added");
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
