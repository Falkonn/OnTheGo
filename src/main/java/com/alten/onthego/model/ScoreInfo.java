/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.entity.Score;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ka3146
 */
public class ScoreInfo {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();

    public Collection<Score> getScores() {
        Query scoresquery = em.createQuery("SELECT s FROM Score s");
        return (Collection<Score>) scoresquery.getResultList();
    }

    public Collection<Score> getScoresbyTeamId(int teamId) {
        Query scoresquery = em.createQuery("SELECT s FROM Score s where s.teamId =" + "\"" + teamId + "\"");
        return (Collection<Score>) scoresquery.getResultList();
    }

    public Collection<Score> getScoresbyUserId(int userId) {
        Query scoresquery = em.createQuery("SELECT s FROM Score s where s.userId =" + "\"" + userId + "\"");
        return (Collection<Score>) scoresquery.getResultList();
    }

}
