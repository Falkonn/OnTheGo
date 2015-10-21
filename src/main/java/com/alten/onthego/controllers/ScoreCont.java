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
    public static boolean addScores(@RequestBody String useranswers, HttpServletResponse response) {

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(useranswers);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //JsonObject jsonArgs = jsonObject.getAsJsonObject("args");
        int taskId = jsonObject.get("taskId").getAsInt();
        int userId = jsonObject.get("userId").getAsInt();
        String answer = jsonObject.get("answer").getAsString();
        boolean isDone = jsonObject.get("taskDone").getAsBoolean();

        //getting the points of that task
        boolean result = ScoreFunctionality.scoreFunction(taskId, userId, answer, isDone);
        if (result == true) {
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Score Has been added");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("Error in adding the score");
        }
        return result;
    }
// the main method just for testing

    public static void main(String[] args) {
        //System.out.println("test  " + addScores("1 19 ghfgf"));

    }
}
