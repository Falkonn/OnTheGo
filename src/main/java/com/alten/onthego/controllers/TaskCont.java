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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
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
    public List<Object> getTasksAndPoints(@PathVariable("data") String data) {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(data);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int userId = jsonObject.get("userId").getAsInt();
        int teamId = jsonObject.get("teamId").getAsInt();
        
        System.out.print("userId"+ userId + "teamId" + teamId);
        
        List<Task> taskList;
        TaskInfo task = new TaskInfo();
        List<Object> taskAndScore = new ArrayList<Object>();
        ScoreInfo scores = new ScoreInfo();
        taskList = (List<Task>) task.findAllTasks();
        List<Object> tasks =  new ArrayList<Object>(taskList);
        for(int i=0; i< taskList.size(); i++)
        {
            taskAndScore.add(tasks.get(i));
            if(taskList.get(i).getIsPersonal()){
                List scoreData = scores.getScoresByTaskIdAndUserId(taskList.get(i).getTaskId(), userId);
                taskAndScore.add(scoreData);
            }
            else{
                List<Object> scoreData = scores.getScoresByTaskIdAndTeamId(taskList.get(i).getTaskId(), teamId);
                taskAndScore.add(scoreData);
            }
        }
        return taskAndScore;
    }
    
 
}
