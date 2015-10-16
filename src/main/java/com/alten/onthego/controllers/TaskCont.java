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
            value = "/TasksAndPoints/{taskid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasksAndPoints(@PathVariable("taskid") int taskid) {
        List<Score> scoreList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        TaskInfo task = new TaskInfo();
        ScoreInfo scores = new ScoreInfo();
        scoreList = scores.getScoresbyTaskId(taskid);
        taskList = task.getTasksbyTaskId(taskid);
        List<Object> finalList = new ArrayList<Object>(scoreList);
        //finalList.add(taskList);
        return taskList;
    }

}
