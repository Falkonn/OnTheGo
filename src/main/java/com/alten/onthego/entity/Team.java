package com.alten.onthego.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Column(name = "team_name")
    private String teamName;

    public Team() {

    }

    public Team(Team teamId, String teamName) {
        this.team = teamId;
        this.teamName = teamName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
