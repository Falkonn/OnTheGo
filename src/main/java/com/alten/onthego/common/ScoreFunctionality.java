/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.common;

import com.alten.onthego.crud.deleteScore;
import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Task;
import com.alten.onthego.entity.User;
import com.alten.onthego.model.ScoreInfo;
import com.alten.onthego.model.TaskInfo;
import com.alten.onthego.model.UserInfo;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ka3146
 */
public class ScoreFunctionality {

    public static boolean scoreAdded, taskstatus1 = false;
    public static boolean taskDone = false;
    public static List<Task> pointList, allTasks;
    public static List<Integer> teamList, scoreList;
    public static List<Integer> usersList, socoreIds, possibleUser;
    public static List<Score> teamSocreList, personSocreList, socoreIdList, taskStatus;
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    public static EntityManager em = emf.createEntityManager();

    public static boolean scoreFunction(long taskId, int userId, String answer, boolean add) {
        ScoreInfo scoreCounter = new ScoreInfo();
        TaskInfo taskinfo = new TaskInfo();
        UserInfo userinfo = new UserInfo();

        pointList = taskinfo.getScoresbyTaskId(taskId);
        Iterator pintesIterator = pointList.iterator();
        Object point = pintesIterator.next();
        int points = Integer.parseInt(point.toString());
        taskStatus = scoreCounter.checkTaskDone(userId);
        teamList = userinfo.getTeamIdbyUserId(userId);

        possibleUser = userinfo.getUserByTeamIdAndTaskId(teamList.get(0), taskId);

        if (!taskStatus.isEmpty()) {
            taskstatus1 = taskStatus.get(0).isTaskDone();
            System.out.println("the status of the task " + taskstatus1);
        }
        System.out.println("the status of the task " + taskstatus1);
        //get the team id from the user table
        teamList = userinfo.getTeamIdbyUserId(userId);
        Iterator ite = teamList.iterator();
        Object teamid = ite.next();
        Task taskID = em.find(Task.class, taskId);
        User userID = em.find(User.class, userId);

        //check if the task is personal or not
        allTasks = taskinfo.checkPersonalTask(taskId);
        boolean isPersonal = allTasks.get(0).getIsPersonal();
        int teamId = Integer.parseInt(teamid.toString());

        System.out.println("taskid  " + taskID + "userid   " + userID + "answers   " + answer
                + "teamid   " + teamList + "points   " + pointList);
        if (add) {

            if (isPersonal) {
                // get the current score by userid and add the new one by taskid 
                scoreList = scoreCounter.getScoresbyUserId(userId);
                socoreIdList = scoreCounter.getScorIdByUserId(userId);
                if (!taskstatus1 || (taskStatus.isEmpty())) {
                    scoreCounter.addSocre(teamId, points, true, answer, taskID, userID);
                    scoreAdded = true;
                }
            } else {
                User UserId = em.find(User.class, userId);
                System.out.println("user id " + userId);
                // get the current score by userid and add the new one by taskid 
                if (possibleUser.isEmpty()) {
                    if (!taskstatus1 || (taskStatus.isEmpty())) {
                        //check in the db if there is an entry with the team id and task id 

                        scoreCounter.addSocre(teamId, points, true, answer, taskID, UserId);
                        scoreAdded = true;
                    }
                } else {
                    System.out.println("The entry already exists");
                }
            }
        } else {
            if ((!possibleUser.isEmpty()) && (userId == possibleUser.get(0))) {

                if (isPersonal) {
                    //get the scoreid from the userId 
                    System.out.println("Testtt");
                    scoreList = scoreCounter.getScorIdByUserId(userId);
                    deleteScore ds = new deleteScore();
                    ds.execute(scoreList.get(0));
                } else {
                    //get the scoreid from the userId 
                    scoreList = scoreCounter.getScorIdByUserId(userId);
                    deleteScore ds = new deleteScore();
                    ds.execute(scoreList.get(0));
                }
            } else {
                System.out.println("The User is not allowed to delete this entry");
            }
        }
        if (scoreAdded) {
            System.out.println("Score has been added");
        } else {
            System.out.println("Error in adding/updating the score");
        }
        return (scoreAdded);
    }

    public static int scoreSum(long teamId) {
        int finalSum = 0;
        ScoreInfo scoreCounter = new ScoreInfo();
        finalSum = scoreCounter.getScoreSumbyTeamId(teamId);
        return finalSum;
    }

    public static void main(String[] args) {
        System.out.println("sum is  " + scoreSum(1));
    }
}
