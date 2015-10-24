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

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(data);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int userId = jsonObject.get("userId").getAsInt();
        int teamId = jsonObject.get("teamId").getAsInt();

        Collection<Task> taskList;
        TaskInfo taskInfo = new TaskInfo();
        ScoreInfo scores = new ScoreInfo();
        taskList = taskInfo.findAllTasks();
        
        Iterator taskIter = taskList.iterator();
        String taskSerialized;
        String scoreSerialized;
        String taskAndScoreSerialized;
        List<Score> scoreData = new ArrayList<Score>();
        List<String> taskAndScore = new ArrayList<String>();
        Gson gson = new Gson();
        while(taskIter.hasNext())
        {   
            Task task = (Task)taskIter.next();
            if(task.getIsPersonal()){
                scoreData = scores.getScoresByTaskIdAndUserId(task.getTaskId(), userId);
            }
            else{
                scoreData = scores.getScoresByTaskIdAndTeamId(task.getTaskId(), teamId);
            }
            if(!scoreData.isEmpty())
                scoreSerialized = gson.toJson(scoreData.get(0));
            else
                scoreSerialized = gson.toJson(new Score());
  
            taskSerialized = gson.toJson(task);
            
            JSONObject taskJson = new JSONObject(taskSerialized);
            JSONObject scoreJson = new JSONObject(scoreSerialized);
            JSONObject taskAndScoreJson = new JSONObject();
   
            if (taskJson.length()>0){
                       taskAndScoreJson = new JSONObject(taskJson, JSONObject.getNames(taskJson));
            }
            if (scoreJson.length()>0){
                for(String key : JSONObject.getNames(scoreJson))
                {
                    if(key.equals("taskDone") || key.equals("userAnswer") || key.equals("user"))
                        taskAndScoreJson.put(key, scoreJson.get(key));
                }
            }
            
            //taskAndScoreSerialized = "[" + taskSerialized + ", " + scoreSerialized + "]";
            taskAndScore.add(taskAndScoreJson.toString());
        }
        
        return taskAndScore;
    }
    
}
