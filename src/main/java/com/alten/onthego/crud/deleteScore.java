/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.crud;

import com.alten.onthego.entity.Score;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author ka3146
 */
public class deleteScore {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();
    boolean result = false;

    public boolean execute(int scoreId) {
        try {
            Score score = em.find(Score.class, scoreId);
            if (score != null) {
                EntityTransaction entityTx = em.getTransaction();
                entityTx.begin();
                em.remove(score);
                entityTx.commit();
                System.out.println();
                System.out.println("The deletion has been done!");
                result = true;
            } else {
                System.out.println("Tasks table is empty!");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            em.close();
        }
        return result;
    }
}
