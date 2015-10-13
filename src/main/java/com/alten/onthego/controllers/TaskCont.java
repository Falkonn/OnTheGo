/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.entity.Task;
import com.alten.onthego.entity.User;
import com.alten.onthego.model.TaskInfo;
import java.util.ArrayList;
import org.springframework.http.MediaType;
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
}
