package com.alten.onthego.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
@Table(name = "\"Task\"")

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long taskId;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    //private List<Score> Scores;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_info")
    private String taskInfo;
    @Column(name = "task_point")
    private Integer taskPoint;
    @Column(name = "is_personal")
    private Boolean isPersonal;
    @Column(name = "task_time")
    private String taskTime;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "answers")
    private String answers;
    @Column(name = "task_theme")
    private String taskTheme;

    public Task() {

    }

    public Task(long taskId, String taskName, String taskInfo, Integer taskPoint,
            Boolean isPersonal, String taskTime, String taskType, String answers, String taskTheme) {

        this.taskId = taskId;
        this.taskName = taskName;
        this.taskInfo = taskInfo;
        this.taskPoint = taskPoint;
        this.isPersonal = isPersonal;
        this.taskTime = taskTime;
        this.taskType = taskType;
        this.answers = answers;
        this.taskTheme = taskTheme;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskTheam(String taskTheme) {
        this.taskTheme = taskTheme;
    }

    public String getTaskTheam() {
        return taskTheme;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Integer getTaskPoint() {
        return taskPoint;
    }

    public void setTaskPoint(Integer taskPoint) {
        this.taskPoint = taskPoint;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Task data are [ First taskid= " + taskId + " " + "task name= " + taskName
                + " " + "task info= " + taskInfo + " " + " " + "taskpoint= " + taskPoint
                + " " + "ispersonal= " + isPersonal + " " + "task time= " + taskTime + " " + "taskType= " + taskType + " " + "answers= " + answers + "]";

    }

}
