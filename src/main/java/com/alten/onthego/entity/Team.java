package com.alten.onthego.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "team_id")
    private User user;

    @Column(name = "team_name")
    private String teamName;

    public Team() {

    }

    public Team(User teamId, String teamName) {
        this.user = teamId;
        this.teamName = teamName;
    }

    public User getTeam() {
        return user;
    }

    public void setTeam(User user) {
        this.user = user;
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
