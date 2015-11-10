/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import com.alten.onthego.common.ScoreFunctionality;
import com.alten.onthego.entity.Score;
import com.alten.onthego.entity.Team;
import com.alten.onthego.model.ScoreInfo;
import com.alten.onthego.model.TeamInfo;
import com.alten.onthego.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
    public String addScores(@RequestBody String useranswers, HttpServletResponse response) {

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
            return null;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.addIntHeader(answer, userId);
            ScoreInfo scoreInfo = new ScoreInfo();
            UserInfo userInfo = new UserInfo();
            List<Score> scoreList;
            List<Long> teamIdList;
            long teamId;
            if ((teamIdList = userInfo.getTeamIdbyUserId(userId)) != null) {
                teamId = teamIdList.get(0);
                if ((scoreList = scoreInfo.getScoresByTaskIdAndTeamId(taskId, teamId)) != null) {
                    Gson gson = new Gson();
                    String score = gson.toJson(scoreList.get(0));
                    return score;
                } else {
                    return null;
                }
            }
            return null;
        }
    }

    @RequestMapping(
            value = "/scoreboard/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int getScoreSum(@PathVariable("id") int teamId) {
        ScoreFunctionality scoreboardsum = new ScoreFunctionality();
        int scoreSum = scoreboardsum.scoreSum(teamId);

        return scoreSum;
    }

    @RequestMapping(
            value = "/teamsAndScores",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTeamsAndScores() {
        TeamInfo teamInfo = new TeamInfo();
        Collection<Team> allTeams = teamInfo.findAllTeams();
        Iterator teamIter = allTeams.iterator();
        ScoreFunctionality scoreFunc = new ScoreFunctionality();

        class TeamAndScore {

            public TeamAndScore(Team team, int scoreSum) {
                this.team = team;
                this.scoreSum = scoreSum;
            }
            Team team;

            public Team getTeam() {
                return team;
            }

            public void setTeam(Team team) {
                this.team = team;
            }
            int scoreSum;

            public int getScoreSum() {
                return scoreSum;
            }

            public void setScoreSum(int scoreSum) {
                this.scoreSum = scoreSum;
            }
        }

        List<TeamAndScore> teamAndScores = new ArrayList<TeamAndScore>();

        while (teamIter.hasNext()) {
            Team team = (Team) teamIter.next();
            int scoreSum = scoreFunc.scoreSum((int) team.getTeamId());
            teamAndScores.add(new TeamAndScore(team, scoreSum));

        }

//        Collections.sort(teamAndScores, (TeamAndScore o1, TeamAndScore o2) -> o1.getScoreSum()<o2.getScoreSum()?-1
//                :o1.getScoreSum()>o2.getScoreSum()?1
//                        :0);
        System.out.print("akhsd" + teamAndScores.get(0).getScoreSum());
        Gson gson = new Gson();
        String serializedTeamAndScores = gson.toJson(teamAndScores);
        return serializedTeamAndScores;
    }
//
//    @RequestMapping(
//            value = "/socreinorder",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public static Collection<Integer> getScoreInOrder() {
//        ScoreInfo scoreOrder = new ScoreInfo();
//        Collection<Integer> scorelist;
//       // scorelist = scoreOrder.getScoreInOrder();
//        TeamInfo teamInfo = new TeamInfo();
//        Collection<Team> allTeams = teamInfo.findAllTeams();
//        Iterator teamIter = allTeams.iterator();
//        
//     //   Iterator it = scorelist.iterator();
//        
//        class TeamAndScore {
//            private Team team;
//            private int scoreSum;
//            private int position;
//
//            public int getPosition() {
//                return position;
//            }
//
//            public void setPosition(int position) {
//                this.position = position;
//            }
//            
//            public TeamAndScore(Team team, int scoreSum) {
//                this.team = team;
//                this.scoreSum = scoreSum;
//            }         
//
//            public Team getTeam() {
//                return team;
//            }
//
//            public void setTeam(Team team) {
//                this.team = team;
//            }
//            
//
//            public int getScoreSum() {
//                return scoreSum;
//            }
//
//            public void setScoreSum(int scoreSum) {
//                this.scoreSum = scoreSum;
//            }
//        }
//        
//        int pos = 0;
//        int prevScore = 50000;
//        
//        while (it.hasNext()) {     
//            int score = (int)it.next();
//            if(prevScore > score){
//                pos++;
//                prevScore = score;
//            }
//            
//            System.out.println("the score are " + pos + " "+ score );
//        }
//
//      
//      
//        return scorelist;
//    }
//    
//    public static void main(String[] args) {
//        ScoreInfo s = new ScoreInfo();
//        System.out.println("the order is" + s.getScoreInOrder());
//    }
}

