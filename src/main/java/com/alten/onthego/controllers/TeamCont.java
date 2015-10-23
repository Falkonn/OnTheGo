/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import java.util.Collection;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alten.onthego.entity.Team;
import com.alten.onthego.model.TeamInfo;
import java.util.Iterator;

@RestController
public class TeamCont {

    @RequestMapping(
            value = "/allteams",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Team> getAllTeams() {
        TeamInfo allTeams = new TeamInfo();
        return (Collection<Team>) allTeams.findAllTeams();
    }  
}