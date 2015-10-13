package com.alten.onthego.crud;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Khaled
 */
import com.alten.onthego.common.PassEncryption;
import com.alten.onthego.entity.Task;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author ka3146
 */
public class addTask {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        try {
      
            Task task = new Task("taskname" , "taskinfo" , 4 , true);
            EntityTransaction entityTx = em.getTransaction();
            entityTx.begin();
            em.persist(task);
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