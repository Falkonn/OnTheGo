/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.entity.Score;
import com.alten.onthego.model.ScoreInfo;
import java.util.ArrayList;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ka3146
 */
@RestController
public class ScoreCont {

    @RequestMapping(
            value = "/Scores",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Score> getScore() {
        ScoreInfo score = new ScoreInfo();
        return new ArrayList<Score>(score.getScores());
    }
}
