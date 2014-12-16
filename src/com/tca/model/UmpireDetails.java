/**
 * 
 */
package com.tca.model;

import java.util.HashMap;

/**
 * @author rahumani
 *
 */
public class UmpireDetails {
    String matchId;
    HashMap<Integer, Over> matchDetails;
    String battingTeam;
    Integer curOverToSave;

    public UmpireDetails() {
        matchDetails = new HashMap<Integer, Over>();
    }
    public String getMatchId() {
        return matchId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    public Integer getCurOverToSave() {
        return curOverToSave;
    }
    public void setCurOverToSave(Integer curOverToSave) {
        this.curOverToSave = curOverToSave;
    }
    @Override
    public String toString() {
        String buffer = "Umpiring Details[Match ID: " + matchId
                + "Team batting " + battingTeam + "Match Details "
                + matchDetails.toString();
        return buffer;
    }
    public String getBattingTeam() {
        return battingTeam;
    }
    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }
    public HashMap<Integer, Over> getMatchDetails() {
        return matchDetails;
    }
    public void setMatchDetails(HashMap<Integer, Over> matchDetails) {
        this.matchDetails = matchDetails;
    }
    
}