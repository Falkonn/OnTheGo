/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.entity.Team;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ka3146
 */
public class TeamInfo {

    private static List<Team> teaminfo = new ArrayList<Team>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();

    public Collection<Team> findAllTeams() {
        Query allTeamsquery = em.createQuery("SELECT f FROM Team f");
        return (Collection<Team>) allTeamsquery.getResultList();
    }
}
