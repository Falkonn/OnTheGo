/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.common.ScoreFunctionality;
import com.alten.onthego.entity.Score;
import com.alten.onthego.model.ScoreInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(
            value = "/answers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addScores(@RequestBody String useranswers, HttpServletResponse response) {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(useranswers);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //JsonObject jsonArgs = jsonObject.getAsJsonObject("args");
        long taskId = jsonObject.get("taskId").getAsLong();
        int userId = jsonObject.get("userId").getAsInt();
        String answer = jsonObject.get("answer").getAsString();
        boolean isDone = jsonObject.get("taskDone").getAsBoolean();

        //getting the points of that task
        ScoreFunctionality sf = new ScoreFunctionality();
        boolean result = sf.scoreFunction(taskId, userId, answer, isDone);
        if (result == true) {
            response.setStatus(HttpServletResponse.SC_OK);
            //System.out.println("Score Has been added");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.addIntHeader(answer, userId);
        }
        return result;
    }

    @RequestMapping(
            value = "/scoreboard/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int getScoreSum(@PathVariable("id") long teamId) {
        ScoreFunctionality scoreboardsum = new ScoreFunctionality();
        return scoreboardsum.scoreSum(teamId);
    }
}
