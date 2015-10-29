/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Task;
import com.alten.onthego.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
    

    public List<Score> getScoresbyTeamId(int teamId) {
        Query scoresquery = em.createQuery("SELECT s FROM Score s where s.teamId =" + teamId);
        return (List<Score>) scoresquery.getResultList();
    }
    
    
    public int getScoreSumbyTeamId(int teamId) {
        Query getScoreSumbyTeamIdquery = em.createQuery("SELECT SUM(s.point) FROM Score s where s.teamId =" + teamId);

        if (getScoreSumbyTeamIdquery.getResultList().get(0) == null){
            return 0;
        }
        else{
            return (int)(long) getScoreSumbyTeamIdquery.getResultList().get(0);
        }
    }

    public List<Integer> getScoresbyUserId(int userId) {
        Query scoresbyUserquery = em.createQuery("SELECT s.scoreId FROM Score s where s.user.userId =" + userId);
        return (List<Integer>) scoresbyUserquery.getResultList();
    }

    public List<Score> getScoresbyScoreId(int scoreid) {
        Query scoresbyscorequery = em.createQuery("SELECT s.point FROM Score s where s.scoreId =" + scoreid);
        return (List<Score>) scoresbyscorequery.getResultList();
    }

    public List<Score> getScoresbyTaskId(int taskId) {
        Query scoresbyTaskquery = em.createQuery("SELECT s.point, s.taskDone FROM Score s where s.task.taskId =" + taskId);
        List untypedScores = scoresbyTaskquery.getResultList();
        List<Score> scores = new ArrayList<>();
        for(Object untypedScoreObject: untypedScores) {
            Object[] untypedScoreData = (Object[]) untypedScoreObject;
            Score score = new Score();
            int points = (int) untypedScoreData[0];
            boolean isTaskDone = (boolean) untypedScoreData[1];  
            score.setPoint(points);
            score.setTaskDone(isTaskDone);
            scores.add(score);
        }
        return scores;
    }
    
    public List<Score> getScoresByTaskIdAndUserId(long taskId, int userId){
        Query scoresbyTaskIdAndUserIdquery = em.createQuery("SELECT s FROM Score s where s.task.taskId=" + taskId + " AND s.user.userId=" + userId );
        List<Score> results = scoresbyTaskIdAndUserIdquery.getResultList();
        return results;
    }
    
    public List<Score> getScoresByTaskIdAndTeamId(long taskId, int teamId){
        Query scoresbyTaskIdAndTeamIdquery = em.createQuery("SELECT s FROM Score s where s.task.taskId=" + taskId + " AND s.teamId=" + teamId);
        List<Score> results = scoresbyTaskIdAndTeamIdquery.getResultList();
        return results;
    }
    

    public List<Score> getTaskByScoreId(int scoreId) {
        Query scoresbyTaskquery = em.createQuery("SELECT s.task.taskId FROM Score s where s.scoreId =" + scoreId);
        return (List<Score>) scoresbyTaskquery.getResultList();
    }

    public List getScorIdByUserId(int userId) {
        Query scoreIdbyUseridQuery = em.createQuery("SELECT s.scoreId FROM Score s where s.user.userId =" + userId);
        return scoreIdbyUseridQuery.getResultList();
    }
    
    public List getScorIdByUserIdAndTaskId(int userId, long taskId) {
        Query scoreIdbyUseridQuery = em.createQuery("SELECT s.scoreId FROM Score s where s.user.userId =" + userId + " AND s.task.taskId =" + taskId);
        return scoreIdbyUseridQuery.getResultList();
    }

    public List<Score> checkTaskDone(long taskId, int userId) {
        Query taskDoneQuery = em.createQuery("SELECT s.point, s.taskDone FROM Score s where s.user.userId =" + userId + " AND s.task.taskId =" + taskId);       
        List untypedstatus = taskDoneQuery.getResultList();
        List<Score> scores = new ArrayList<>();
        for(Object untypedStatusObject: untypedstatus) {
            Object[] untypedStatusData = (Object[]) untypedStatusObject;
            Score score = new Score();
            int points = (int) untypedStatusData[0];
            boolean isTaskDone = (boolean) untypedStatusData[1];  
            score.setPoint(points);
            score.setTaskDone(isTaskDone);
            scores.add(score);
        }
        return scores;
    }

    public boolean addScore(int teamId, int point, boolean taskDone, String userAnswer, Task taskid, User userid) {
        boolean socreAdded = false;
        try {

            Score score = new Score(teamId, point, taskDone, userAnswer, taskid, userid);
            EntityTransaction entityTx = em.getTransaction();
            entityTx.begin();
            em.persist(score);
            entityTx.commit();
            System.out.println("The score row has been successfully added");
            socreAdded = true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return socreAdded;
    }

}