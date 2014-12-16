/**
 * 
 */
package com.tca.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.tca.model.Extras;
import com.tca.model.Score;

/**
 * @author rahumani
 *
 */
public class ScoreHelper {

    private static ScoreHelper instance;
    
    public static ScoreHelper getInstance() {
        if (instance == null) {
            instance = new ScoreHelper();
        }
        return instance;
    }
    /**
     * This API will analyse the delivery bowled and return the result in
     * score class.
     * @param ball
     * @return
     */
    public Score analyzeBall(String ball) {
        Score score = new Score();
        Integer runs = 0;
        Integer wickets = 0;
        Extras extra = new Extras();
        
        List<String> curDeliveryList = new ArrayList<String>();
        if (ball.contains("+")) {
            curDeliveryList.add(ball.substring(0, ball.indexOf("+")));
            curDeliveryList.add(ball.substring(ball.indexOf("+") + 1));
        } else {
            curDeliveryList.add(ball);
        }
        
        for (int count = 0; count < curDeliveryList.size(); count++) {
            String tempBall = curDeliveryList.get(count);
            if (tempBall.contains("Y")) {
                Integer wides = 1;
                extra.incrementWide(wides);
                runs++;
            } else if (tempBall.contains("NB")) {
                Integer noBall = 1;
                extra.incrementNoBall(noBall);
                runs++;
            } else if (tempBall.contains("B")) {
                Integer byes = 1;
                extra.incrementByes(byes);
                runs++;
                score.setBallCount(1);
            } else if (tempBall.contains("W")) {
                wickets++;
                score.setBallCount(1);
            } else {
                runs = runs + Integer.parseInt(tempBall);
                score.setBallCount(1);
            }
        }
        score.setRuns(runs);
        score.setWickets(wickets);
        score.setExtras(extra);
        return score;
    }
    
    /**
     * API to calculate the runs/wickets in an over.
     * @param curOverDetails
     * @return
     */
    public Score getOverRuns (String curOverDetails) {
        Score score = new Score();
        Integer runs = 0;
        Integer wickets = 0;
        Extras extra = new Extras();
        
        String[] curOverList = curOverDetails.split(",");
        for (int count = 0; count < curOverList.length; count++) {
            String ball = curOverList[count];
            Score tempScore = null;
            if (ball.contains("+")) {
                List<String> curDeliveryList = new ArrayList<String>(2);
                curDeliveryList.add(ball.substring(0, ball.indexOf("+")));
                curDeliveryList.add(ball.substring(ball.indexOf("+") + 1));
                for (int ballCount = 0; ballCount < curDeliveryList.size(); ballCount++) {
                    String tempBall = curDeliveryList.get(ballCount);
                    tempScore = analyzeBall(tempBall);
                }
            } else {
                tempScore = analyzeBall(ball);
            }
            if (tempScore != null) {
                runs = runs + tempScore.getRuns();
                wickets = wickets + tempScore.getWickets();
                extra.incrementByes(tempScore.getExtras().getByes());
                extra.incrementNoBall(tempScore.getExtras().getNoBall());
                extra.incrementWide(tempScore.getExtras().getWides());
            }
        }
        score.setRuns(runs);
        score.setWickets(wickets);
        score.setExtras(extra);
        return score;
    }
    
    /**
     * Get the score after the over with the previoud score inputted.
     * @param previousScore
     * @param curOverDetails
     * @return
     */
    public String getScore(String previousScore, String curOverDetails) {

        Score score = new Score();
        if (previousScore != null) {
            score.setRuns(Integer.parseInt(previousScore.substring(0,
                    previousScore.indexOf("/"))));
            score.setWickets(Integer.parseInt(previousScore
                    .substring(previousScore.indexOf("/") + 1)));
            Log.i("Rahul", "Previous Score Runs is " + score.getRuns());
            Log.i("Rahul", "Previous Score Wickets is " + score.getWickets());
        }
        Log.i("Rahul", "Score is "+curOverDetails);
        Score tempScore = getOverRuns(curOverDetails);
        score.setRuns(score.getRuns() + tempScore.getRuns());
        score.setWickets(score.getWickets() + tempScore.getWickets());
        
        return score.getRuns() + "/" + score.getWickets();
    }
}
