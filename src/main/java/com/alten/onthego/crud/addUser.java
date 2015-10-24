/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.entity.Team;
import com.alten.onthego.entity.User;
import java.util.List;
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
            Team team = em.find(Team.class, 1L);
            User user = new User(19,"Vasileios", "Golematis", "vasileios.golematis@alten.com", "0767649596", "Goteborg", "IT", "WFNbUN7gIoU=", team, "0");
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
