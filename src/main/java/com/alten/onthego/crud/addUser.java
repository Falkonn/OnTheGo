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

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();
        try {
            PassEncryption pe = new PassEncryption();
            User user = new User("Khaled", "Alnawasreh", "khaled.nawasreh@gmail.com", "088888323232", "goteborg", "IT", "dksda123", 1);
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
