/**
 * 
 */
package com.tca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author rahumani
 *
 */
public class Innings implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String teamName;
    private List<Batsman> batsmans;
    private List<Bowler> bowlers;
    private Extras extra;
    private Score score;
    private List<FOW> fallOfWickets;
    
    public Innings(String team) {
        batsmans = new ArrayList<Batsman>();
        bowlers = new ArrayList<Bowler>();
        extra = new Extras();
        score = new Score();
        teamName = team;
        fallOfWickets = new ArrayList<FOW>();
    }
    /**
     * @return the batsmans
     */
    public List<Batsman> getBatsmans() {
        return batsmans;
    }
    /**
     * @param batsmans the batsmans to set
     */
    public void setBatsmans(List<Batsman> batsmans) {
        this.batsmans = batsmans;
    }
    /**
     * @return the bowlers
     */
    public List<Bowler> getBowlers() {
        return bowlers;
    }
    /**
     * @param bowlers the bowlers to set
     */
    public void setBowlers(List<Bowler> bowlers) {
        this.bowlers = bowlers;
    }
    /**
     * @return the extra
     */
    public Extras getExtra() {
        return extra;
    }
    /**
     * @param extra the extra to set
     */
    public void setExtra(Extras extra) {
        this.extra = extra;
    }
    /**
     * @return the score
     */
    public Score getScore() {
        return score;
    }
    /**
     * @param score the score to set
     */
    public void setScore(Score score) {
        this.score = score;
    }
    
    public Batsman newBatsman(String batsmanName) {
        Batsman batsman = new Batsman(batsmanName);
        batsmans.add(batsman);
        return batsman;
    }
    
    public Bowler newBowler(String bowlerName) {
        Bowler bowler = new Bowler(bowlerName);
        bowlers.add(bowler);
        return bowler;
    }
    
    public void updateScore(Score scorePerBall, boolean undo) {
        if (undo) {
            score.setRuns(score.getRuns() - scorePerBall.getRuns());
            score.setWickets(score.getWickets() - scorePerBall.getWickets());
            score.setBallCount(score.getBallCount()
                    - scorePerBall.getBallCount());
            Extras extra = score.getExtras();
            extra.setByes(extra.getByes() - scorePerBall.getExtras().getByes());
            extra.setNoBall(extra.getNoBall()
                    + scorePerBall.getExtras().getNoBall());
            extra.setWides(extra.getWides()
                    + scorePerBall.getExtras().getWides());
            score.setExtras(extra);
        } else {
            score.setRuns(score.getRuns() + scorePerBall.getRuns());
            score.setWickets(score.getWickets() + scorePerBall.getWickets());
            score.setBallCount(score.getBallCount() + scorePerBall.getBallCount());
            Extras extra = score.getExtras();
            extra.incrementByes(scorePerBall.getExtras().getByes());
            extra.incrementNoBall(scorePerBall.getExtras().getNoBall());
            extra.incrementWide(scorePerBall.getExtras().getWides());
            score.setExtras(extra);
        }
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the fallOfWickets
     */
    public List<FOW> getFallOfWickets() {
        return fallOfWickets;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String buffer = "Innings [" +batsmans.toString() +" ]";
        return buffer;
    }

    /**
     * @param fallOfWickets the fallOfWickets to set
     */
    public void setFallOfWickets(List<FOW> fallOfWickets) {
        this.fallOfWickets = fallOfWickets;
    }
    
    /**
     * Add to the fallOfWickets List
     * @param fallOfWicket 
     */
    public void addFallOfWickets(FOW fallOfWicket) {
        fallOfWickets.add(fallOfWicket);
    }
    
    /**
     * Get the fall Of Wicket
     * @param fallOfWicket
     */
    public FOW getFallOfWicketsByBatsman(String batsmanName) {
        Iterator<FOW> FOWs = fallOfWickets.iterator();
        while (FOWs.hasNext()) {
            FOW fow = FOWs.next();
            if (fow.getBatsmanName().equalsIgnoreCase(batsmanName)) {
                return fow;
            }
        }
        return null;
    }
    
}
