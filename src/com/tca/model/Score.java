/**
 * 
 */
package com.tca.model;

import java.io.Serializable;

/**
 * @author rahumani
 * 
 */
public class Score implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer runs;
    private Integer wickets;
    private Extras extras;
    private Integer ballCount;

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Score() {
        extras = new Extras();
        runs = 0;
        wickets = 0;
        ballCount = 0;
    }

    /**
     * @return the extras
     */
    public Extras getExtras() {
        return extras;
    }

    /**
     * @param extras
     *            the extras to set
     */
    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    /**
     * @return the ballCount
     */
    public Integer getBallCount() {
        return ballCount;
    }

    /**
     * @param ballCount
     *            the ballCount to set
     */
    public void setBallCount(Integer ballCount) {
        this.ballCount = ballCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String buffer = "Score Details [ Runs: " + runs + ", Wickets " + wickets
                +", "+extras.toString() +"]";
        return buffer;

    }

}
