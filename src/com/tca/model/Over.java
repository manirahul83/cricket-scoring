/**
 * 
 */
package com.tca.model;

/**
 * @author rahumani
 *
 */
public class Over {
    Integer overNo;
    String bowler;
    String overDetails;
    
    
    public Over(Integer overNo, String bowler, String overDetails) {
        super();
        this.overNo = overNo;
        this.bowler = bowler;
        this.overDetails = overDetails;
    }
    
    public Integer getOverNo() {
        return overNo;
    }
    public void setOverNo(Integer overNo) {
        this.overNo = overNo;
    }
    public String getBowler() {
        return bowler;
    }
    public void setBowler(String bowler) {
        this.bowler = bowler;
    }
    public String getOverDetails() {
        return overDetails;
    }
    public void setOverDetails(String overDetails) {
        this.overDetails = overDetails;
    }
    
    @Override
    public String toString() {
        String buffer = "Over Details[Over#: " + overNo + "Bowler " + bowler
                + "Over Details " + overDetails;
        return buffer;
    }
}
