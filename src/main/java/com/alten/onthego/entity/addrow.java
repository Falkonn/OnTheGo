package com.alten.onthego.entity;

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
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author ka3146
 */
public class addrow {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        try {
            User user = new User("Mattias" , "Isene" ,"mattias.isene@alten.se" , "IT Systems" , "07023532489" , "Consultant", "2345");
            PassEncryption pe = new PassEncryption();
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