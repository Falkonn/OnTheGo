/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Task;
import com.alten.onthego.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ka3146
 */
public class addScore {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dlapp");
        EntityManager em = emf.createEntityManager();

        try {
            Task task = em.find(Task.class, 1L);
            User user = em.find(User.class, 1L);
//(int teamId, int point, boolean taskDone, String userAnswer, Task taskid, User userid)
            Score score = new Score(1, 22, true, "hello", task, user);
            EntityTransaction entityTx = em.getTransaction();
            entityTx.begin();
            em.persist(score);
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
