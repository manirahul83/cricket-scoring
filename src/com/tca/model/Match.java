/**
 * 
 */
package com.tca.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author rahumani
 *
 */
public class Match implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String matchId;
    private String tossWonBy;
    private HashMap<Integer, Innings> innings;
    
    public Match(String matchId, String team1, String team2) {
        this.matchId = matchId;
        innings = new HashMap<Integer, Innings>();
        innings.put(0, new Innings(team1));
        innings.put(1, new Innings(team2));
    }
    /**
     * @return the matchId
     */
    public String getMatchId() {
        return matchId;
    }
    /**
     * @param matchId the matchId to set
     */
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    /**
     * @return the tossWonBy
     */
    public String getTossWonBy() {
        return tossWonBy;
    }
    /**
     * @param tossWonBy the tossWonBy to set
     */
    public void setTossWonBy(String tossWonBy) {
        this.tossWonBy = tossWonBy;
    }
    /**
     * @return the innings
     */
    public HashMap<Integer, Innings> getInnings() {
        return innings;
    }
    /**
     * @param innings the innings to set
     */
    public void setInnings(HashMap<Integer, Innings> innings) {
        this.innings = innings;
    }
    
    public Innings getInningsById(Integer id) {
        return innings.get(id);
    }
    
    public Batsman newBatsman(Integer id, String name) {
        Innings inning = getInningsById(id);
        Batsman bat = inning.newBatsman(name);
        innings.put(id, inning);
        return bat;
    }
    
    public Bowler newBowler(Integer id, String name) {
        Innings inning = getInningsById(id);
        Bowler bowler = inning.newBowler(name);
        innings.put(id, inning);
        return bowler;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String buffer = "Match [MatchID " + matchId + ", Toss Won By "
                + tossWonBy + ", " + innings.toString();
        return buffer;
    }
}
