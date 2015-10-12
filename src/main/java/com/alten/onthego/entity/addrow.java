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
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author ka3146
 */
public class addrow {
private static String firstName;
private static String lastName;
private static String email;
private static String department;
private static String telefon;
private static String jobTitle;
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
        EntityManager em = emf.createEntityManager();

        try {
            User user = new User("Mattias" , "Isene" ,"mattias.isene@alten.se" , "IT Systems" , "07023532489" , "Consultant");
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