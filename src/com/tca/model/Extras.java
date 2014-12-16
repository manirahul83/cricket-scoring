/**
 * 
 */
package com.tca.model;

import java.io.Serializable;

/**
 * @author rahumani
 *
 */
public class Extras implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer noBall;
    private Integer wides;
    private Integer byes;
    
    public Extras() {
        noBall = 0;
        wides = 0;
        byes = 0;
    }
    /**
     * @return the noBall
     */
    public Integer getNoBall() {
        return noBall;
    }
    /**
     * @param noBall the noBall to set
     */
    public void setNoBall(Integer noBall) {
        this.noBall = noBall;
    }
    /**
     * @return the wides
     */
    public Integer getWides() {
        return wides;
    }
    /**
     * @param wides the wides to set
     */
    public void setWides(Integer wides) {
        this.wides = wides;
    }
    /**
     * @return the byes
     */
    public Integer getByes() {
        return byes;
    }
    /**
     * @param byes the byes to set
     */
    public void setByes(Integer byes) {
        this.byes = byes;
    }
    
    public void incrementWide(Integer wides) {
        this.wides = this.wides + wides;
    }
    
    public void incrementNoBall(Integer noBall) {
        this.noBall = this.noBall + noBall;
    }
    public void incrementByes(Integer byes) {
        this.byes = this.byes + byes;
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String buffer = "Extras [ Wides: " + wides + ", NoBalls " + noBall
                + ", Byes "+byes +"]";
        return buffer;

    }
    
}
