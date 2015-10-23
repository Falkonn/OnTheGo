/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Task;
import com.alten.onthego.entity.User;
import com.alten.onthego.model.ScoreInfo;
import com.alten.onthego.model.TaskInfo;
import com.alten.onthego.model.UserInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ka3146
 */
@RestController
public class ScoreCont {

    public static boolean scoreAdded = false;
    public static boolean scoreUpdated = false;
    public static boolean taskDone = false;
    public static List<Task> pointList, allTasks;
    public static List<Integer> teamList;
    public static List<Integer> usersList, socoreIds;
    public static List<Score> scoreList, teamSocreList, personSocreList, socoreIdList,taskStatus;
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    public static EntityManager em = emf.createEntityManager();

    @RequestMapping(
            value = "/Scores",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Score> getScore() {
        ScoreInfo score = new ScoreInfo();
        return new ArrayList<Score>(score.getScores());
    }

    @RequestMapping(
            value = "/answers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public static boolean addScores(@RequestBody String useranswers) {

        ScoreInfo scoreCounter = new ScoreInfo();
        TaskInfo taskinfo = new TaskInfo();
        UserInfo userinfo = new UserInfo();

        String[] splited = useranswers.split(" ");
        String taskid = splited[0];
        String userid = splited[1];
        String answers = splited[2];

        //incase the back end recive JSON and not string
        /*JsonParser jsonParser = new JsonParser();
         JsonElement jsonElement = jsonParser.parse(useranswers);
         if (jsonElement.isJsonObject()) {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         String method = jsonObject.get("method").getAsString();
         JsonObject jsonArgs = jsonObject.getAsJsonObject("args");
         String taskid = jsonArgs.get("taskid").getAsString();
         String userid = jsonArgs.get("userid").getAsString();
         String answer =jsonArgs.get("answers").getAsString();
         }*/
        //getting the points of that task
        long taskId = Integer.parseInt(taskid);
        pointList = taskinfo.getScoresbyTaskId(taskId);
        Iterator pintesIterator = pointList.iterator();
        Object point = pintesIterator.next();
        int points = Integer.parseInt(point.toString());
        taskStatus = scoreCounter.checkTaskDone(taskId);
        System.out.println("Task staaatus" + taskStatus.get(1));
        //Iterator taskIterator = taskStatus.get(1).iterator();
        Object task = taskStatus.get(1);
        
        taskDone = Boolean.valueOf(task.toString());
        System.out.println("ttttt" + taskDone);

        //get the team id from the user table
        int UserId = Integer.parseInt(userid);
        teamList = userinfo.getTeamIdbyUserId(UserId);
        Iterator ite = teamList.iterator();
        Object teamid = ite.next();
        Task taskID = em.find(Task.class, taskId);
        User userID = em.find(User.class, UserId);

        //check if the task is personal or not
        allTasks = taskinfo.checkPersonalTask(taskId);
        boolean isPersonal = allTasks.get(0).getIsPersonal();
        int teamId = Integer.parseInt(teamid.toString());

        System.out.println("taskid  " + taskid + "userid   " + userid + "answers   " + answers
                + "teamid   " + teamList + "points   " + pointList);

        if (isPersonal) {
            // get the current score by userid and add the new one by taskid 
            scoreList = scoreCounter.getScoresbyUserId(UserId);
            socoreIdList = scoreCounter.getScorIdByUserId(UserId);
            Iterator scoreiter = scoreList.iterator();
            Object score = scoreiter.next();
            int scores = Integer.parseInt(score.toString());
            if (scoreList.isEmpty() && !taskDone) {
                scoreCounter.addSocre(teamId, points, true, answers, taskID, userID);
                scoreAdded = true;
            }
            updatePersonalScores();
        } else {
            // get the current score by userid and add the new one by taskid 
            teamSocreList = scoreCounter.getScoresbyUserId(UserId);
            if (teamSocreList.isEmpty() && !taskDone) {
                //get the team id from the user table
                usersList = userinfo.findUsersByTeamId(teamId);
                for (int i = 0; i < usersList.size(); i++) {
                    User userId = em.find(User.class, usersList.get(i));
                    System.out.println("user id " + usersList.get(i));
                    // get the current score by userid and add the new one by taskid 
                    scoreCounter.addSocre(teamId, points, true, answers, taskID, userId);
                    scoreAdded = true;
                }
            } else {
                //get the team id from the user table
                usersList = userinfo.findUsersByTeamId(teamId);
                System.out.println("User listtttt " + usersList);
                for (int i = 0; i < usersList.size(); i++) {
                    // User userId = em.find(User.class, usersList.get(i));
                    System.out.println("user id " + usersList.get(i));
                    // get the current score by userid and add the new one by taskid 
                    System.out.println("userlist " + usersList.get(i));
                    System.out.println("ssssss" + (scoreCounter.getScorIdByUserId(usersList.get(i))));
                    socoreIds = scoreCounter.getScorIdByUserId(usersList.get(i));
                    updateTeamScores();
                    // if user has zero score
                    if ((scoreCounter.getScorIdByUserId(usersList.get(i))).isEmpty() && !taskDone) {
                        User userId = em.find(User.class, usersList.get(i));
                        scoreCounter.addSocre(teamId, points, true, answers, taskID, userId);
                    }
                }
            }
        }

        if (scoreAdded || scoreUpdated) {
            System.out.println("Score has been added/updated");
            //response.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("Error in adding/updating the score");
            // response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return (scoreAdded || scoreUpdated);
    }

    private static void updatePersonalScores() {
        Iterator pintesIterator = pointList.iterator();
        Object point = pintesIterator.next();
        int taskpoints = Integer.parseInt(point.toString());
        Iterator personScoreIter = scoreList.iterator();
        Object personscore = personScoreIter.next();
        if (!taskDone) {
            int scores = Integer.parseInt(personscore.toString());
            scores = scores + taskpoints;
            System.out.println("tem score " + scores);

            //get the score id by userid 
            Score score = em.find(Score.class, socoreIdList.get(0));
            System.out.println("score  " + score);
            score.setPoint(scores);
            EntityTransaction entityTx = em.getTransaction();
            entityTx.begin();
            em.merge(score);
            entityTx.commit();
            System.out.println();
            System.out.println("The update has been done!");
            scoreUpdated = true;
        }
    }

    private static void updateTeamScores() {
        Iterator pintesIterator = pointList.iterator();
        Object point = pintesIterator.next();
        int points = Integer.parseInt(point.toString());
        ScoreInfo scoreCounter = new ScoreInfo();
        if (!taskDone) {
            for (int k = 0; k < socoreIds.size(); k++) {

                //get the score id by userid 
                Score score = em.find(Score.class, socoreIds.get(k));
                System.out.println("score  " + score);

                //take each one score and update them in separet way
                System.out.println("score ids " + socoreIds.get(k));
                teamSocreList = scoreCounter.getScoresbyScoreId(socoreIds.get(k));
                System.out.println("teams score list " + teamSocreList);
                Iterator teamScoreIter = teamSocreList.iterator();
                Object teamscore = teamScoreIter.next();
                int personscores = Integer.parseInt(teamscore.toString());
                personscores = personscores + points;
                System.out.println("tem score " + personscores);
                score.setPoint(personscores);
                EntityTransaction entityTx = em.getTransaction();
                entityTx.begin();
                em.merge(score);
                entityTx.commit();
                System.out.println();
                System.out.println("The update has been done!");
                scoreUpdated = true;
            }
        }
    }
// the main method just for testing

    public static void main(String[] args) {
        System.out.println("test  " + addScores("4 19 ghfgf"));

    }
}
