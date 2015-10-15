package com.alten.onthego.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
@Table(name = "Task")

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_info")
    private String taskInfo;
    @Column(name = "task_point")
    private Integer taskPoint;
    @Column(name = "is_personal")
    private Boolean isPersonal;

    public Task() {

    }

    public Task(String taskName, String taskInfo, Integer taskPoint, Boolean isPersonal) {

        this.taskName = taskName;
        this.taskInfo = taskInfo;
        this.taskPoint = taskPoint;
        this.isPersonal = isPersonal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
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

}
