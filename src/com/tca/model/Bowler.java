/**
 * 
 */
package com.tca.model;

import java.io.Serializable;
import java.util.HashMap;

import android.util.Log;

/**
 * @author rahumani
 *
 */
public class Bowler implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer ball;
    private Integer runs;
    private Integer maidens;
    private Integer wickets;
    private Integer wides;
    private Integer noBall;
    private HashMap<Integer, Over> overs;
    
    public Bowler(String name) {
        super();
        this.name = name;
        ball = 0;
        runs = 0;
        maidens = 0;
        wickets = 0;
        wides = 0;
        noBall = 0;
        overs = new HashMap<Integer, Over>();
    }
    
    public Bowler(String name, Integer ball, Integer runs, Integer maidens,
            Integer wickets, Integer wides, Integer noball) {
        super();
        this.name = name;
        this.ball = ball;
        this.runs = runs;
        this.maidens = maidens;
        this.wickets = wickets;
        this.wides = wides;
        this.noBall = noball;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ball
     */
    public Integer getBall() {
        return ball;
    }

    /**
     * @param ball the ball to set
     */
    public void setBall(Integer ball) {
        this.ball = ball;
    }

    public Integer getRuns() {
        return runs;
    }
    public void setRuns(Integer runs) {
        this.runs = runs;
    }
    public Integer getMaidens() {
        return maidens;
    }
    public void setMaidens(Integer maidens) {
        this.maidens = maidens;
    }
    public Integer getWickets() {
        return wickets;
    }
    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }
    public Integer getWides() {
        return wides;
    }
    public void setWides(Integer wides) {
        this.wides = wides;
    }
    public Integer getNoball() {
        return noBall;
    }
    public void setNoball(Integer noball) {
        this.noBall = noball;
    }

    public HashMap<Integer, Over> getOvers() {
        return overs;
    }

    public void setOvers(HashMap<Integer, Over> overs) {
        this.overs = overs;
    }
    
    public void incrWickets() {
        wickets++;
    }
    
    public void incrRuns(Integer runs) {
        this.runs = this.runs + runs;
    }
    
    public void incrBall() {
        this.ball++;
        Log.i("Rahul", "Bowler has bowled "+ball);
    }
    
    public void decrBall() {
        this.ball--;
    }
    
    public void decrWickets() {
        wickets--;
    }
    
    public void decrRuns(Integer runs) {
        this.runs = this.runs - runs;
    }
    
    public void incrWides(Integer wides) {
        this.wides = this.wides + wides;
    }
    
    public void incrNoBall(Integer noBall) {
        this.noBall = this.noBall + noBall;
    }
    public void decrWides(Integer wides) {
        this.wides = this.wides + wides;
    }
    
    public void decrNoBall(Integer noBalls) {
        this.noBall = this.noBall + noBall;
    }
    
    public String getDisplayOvers() {
        return String.valueOf(ball / 6) + "."
        + String.valueOf(ball % 6);
    }
}
