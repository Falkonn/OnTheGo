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
import com.alten.onthego.entity.Task;
import javax.persistence.*;

/**
 *
 * @author ka3146
 */
public class addTask {

    //the main class here just for testing purpose

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dlapp");
        EntityManager em = emf.createEntityManager();

        try {
            Task task = new Task(1,"taskName", "taskInfo", 90, true, "3223", "group", "groupanswer", "inside");
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
