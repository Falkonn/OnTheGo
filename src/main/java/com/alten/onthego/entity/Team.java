package com.alten.onthego.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Team
 *
 */
@Entity
@Table(name="Team")
public class Team implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	@OneToMany
	 	@JoinColumn(name="team_id")
	    private Long id;
	    @Column(name = "team_id")
	    private Integer teamId;
	    @Column(name = "team_name")
	    private String teamName;
 
	public Team() {
		super();
	}
	
	public Team(int teamId, String teamName)
	{
    	super();
		this.teamId = teamId;
		this.teamName = teamName;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
   
}
