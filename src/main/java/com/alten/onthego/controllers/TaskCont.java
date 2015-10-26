/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Task;
import com.alten.onthego.model.ScoreInfo;
import com.alten.onthego.model.TaskInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ka3146
 */
@RestController
public class TaskCont {

    @RequestMapping(
            value = "/Tasks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Task> getTasks() {
        TaskInfo task = new TaskInfo();
        return new ArrayList<Task>(task.findAllTasks());
    }

    @RequestMapping(
            value = "/TasksAndPoints/{data}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getTasksAndPoints(@PathVariable("data") String data) {

        // Parsing userId and teamId
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(data);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int userId = jsonObject.get("userId").getAsInt();
        int teamId = jsonObject.get("teamId").getAsInt();

        System.out.print("userId" + userId + "teamId" + teamId);

        // TasksList and Scores 
        TaskInfo taskInfo = new TaskInfo();
        ScoreInfo scores = new ScoreInfo();
        // Tasks and Scores Serialized 
        String taskSerialized;
        String scoreSerialized;
        // List of all the tasks. 
        Collection<Task> taskList;
        taskList = taskInfo.findAllTasks();
        Iterator taskIter = taskList.iterator();
        // List of Scores
        List<Score> scoreData = new ArrayList<Score>();
        // List of json strings with tasks and scores
        List<String> taskAndScore = new ArrayList<String>();
        Gson gson = new Gson();
        // Iterate through all the tasks and fetch and concatenate
        // the scores for the specific team or user
        while(taskIter.hasNext())
        {   
            Task task = (Task)taskIter.next();
            // If the task is personal fetch the score by the user Id
            if(task.getIsPersonal()){
                scoreData = scores.getScoresByTaskIdAndUserId(task.getTaskId(), userId);
            }
            // If the task is not personal fetch the score by the team Id
            else{
                scoreData = scores.getScoresByTaskIdAndTeamId(task.getTaskId(), teamId);
            }
            // Create a Json String of the score object
            if(!scoreData.isEmpty())
                scoreSerialized = gson.toJson(scoreData.get(0));
            else
                scoreSerialized = gson.toJson(new Score());
            // Json string of task object
            taskSerialized = gson.toJson(task);
            
            // Create Json objects to merge the task and Score
            JSONObject taskJson = new JSONObject(taskSerialized);
            JSONObject scoreJson = new JSONObject(scoreSerialized);
            JSONObject taskAndScoreJson = new JSONObject();
   
            // Merge Json Objects (Task and Score)
            if (taskJson.length()>0){
                       taskAndScoreJson = new JSONObject(taskJson, JSONObject.getNames(taskJson));
            }
            if (scoreJson.length()>0){
                // Here the merging takes place
                for(String key : JSONObject.getNames(scoreJson))
                {
                    // Merge only the fields that matter
                    if(key.equals("taskDone") || key.equals("userAnswer") || key.equals("user"))
                        taskAndScoreJson.put(key, scoreJson.get(key));
                }
            }
            
            // Convert the result to string and add it to the json string list
            taskAndScore.add(taskAndScoreJson.toString());
        }
        // Return the List of Json Lists (tasks, score)
        return taskAndScore;
    }
}
