/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.model;

import com.alten.onthego.entity.Task;
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
public class TaskInfo {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");
    EntityManager em = emf.createEntityManager();

    public Collection<Task> findAllTasks() {
        Query tasksrsquery = em.createQuery("SELECT t FROM Task t");
        return (Collection<Task>) tasksrsquery.getResultList();
    }

    public List<Task> getTasksbyTaskId(int taskId) {
        Query scoresquery = em.createQuery("SELECT t FROM Task t where t.taskId =" + "\"" + taskId + "\"");
        return (List<Task>) scoresquery.getResultList();
    }
}
