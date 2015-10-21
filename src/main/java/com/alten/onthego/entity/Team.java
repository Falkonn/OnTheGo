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
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teamId;

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

    public int getTeamId() {
        return teamId;
    }
}
