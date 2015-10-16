package com.alten.onthego.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Score
 *
 */
@Entity
@Table(name = "SCORE")

public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "score_id")
    private long scoreId;
    
    @Column(name = "team_id")
    private Integer teamId;
    
    @ManyToOne(targetEntity=Task.class)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "point")
    private Integer point;
    @Column(name = "task_done")
    private boolean taskDone;
    @Column(name = "user_answer")
    private String userAnswer;

    public Score() {

    }

    public Score(int teamId, int point, boolean taskDone, String userAnswer, Task taskid, User userid) {
        this.teamId = teamId;
        this.point = point;
        this.taskDone = taskDone;
        this.userAnswer = userAnswer;
        this.task = taskid;
        this.user = userid;

    }

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
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

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
