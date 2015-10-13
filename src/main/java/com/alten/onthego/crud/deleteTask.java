/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

/**
 *
 * @author Khaled
 */

import com.alten.onthego.entity.Task;
import javax.persistence.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class deleteTask {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();
        try {
            Task task = em.find(Task.class, 1L);
            if (task != null) {
                EntityTransaction entityTx = em.getTransaction();
                entityTx.begin();
                em.remove(task);
                entityTx.commit();
                System.out.println();
                System.out.println("The deletion has been done!");
            } else {
                System.out.println("Tasks table is empty!");
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            em.close();
        }
    }
}
