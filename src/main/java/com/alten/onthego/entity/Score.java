package com.alten.onthego.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Score
 *
 */
@Entity
@Table(name = "Score")

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "team_id")
    private Integer teamId;
    @Column(name = "task_id")
    private Integer taskId;
    @Column(name = "point")
    private Integer point;
    @Column(name = "task_done")
    private boolean taskDone;

    public Score() {
        super();
    }

    public Score(int userId, int teamId, int taskId, int point, boolean taskDone) {
        super();
        this.userId = userId;
        this.teamId = teamId;
        this.taskId = taskId;
        this.point = point;
        this.taskDone = taskDone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }

}
