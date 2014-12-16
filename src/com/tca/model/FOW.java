/**
 * 
 */
package com.tca.model;

import java.io.Serializable;

/**
 * @author rahumani
 *
 */
public class FOW implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String batsmanName;
    private Score score;
    /**
     * @return the batsmanName
     */
    public String getBatsmanName() {
        return batsmanName;
    }
    /**
     * @param batsmanName the batsmanName to set
     */
    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
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
}
