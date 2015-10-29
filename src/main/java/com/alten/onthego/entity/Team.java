package com.alten.onthego.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Team
 *
 */
@Entity
@Table(name = "Team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teamId;

    @Column(name = "team_name")
    private String teamName;

    public Team() {

    }

    public Team(String teamName) {

        this.teamName = teamName;
    }
    
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTeamId() {
        return teamId;
    }
}