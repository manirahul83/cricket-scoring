/**
 * 
 */
package com.tca.model;

import java.io.Serializable;

/**
 * @author rahumani
 *
 */
public class Batsman implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer runs;
    private Integer balls;
    private Integer four;
    private Integer six;
    private String out;
    private boolean onStrike;
    
    
    public Batsman(String name) {
        super();
        this.name = name;
        runs = 0;
        balls = 0;
        four = 0;
        six = 0;
        out = "not out";
        onStrike = false;
    }
    
    public Batsman(String name, Integer runs, Integer balls, Integer four,
            Integer six, String out) {
        super();
        this.name = name;
        this.runs = runs;
        this.balls = balls;
        this.four = four;
        this.six = six;
        this.out = out;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOut() {
        return out;
    }
    public void setOut(String out) {
        this.out = out;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getBalls() {
        return balls;
    }

    public void setBalls(Integer balls) {
        this.balls = balls;
    }

    public Integer getFour() {
        return four;
    }

    public void setFour(Integer four) {
        this.four = four;
    }

    public Integer getSix() {
        return six;
    }

    public void setSix(Integer six) {
        this.six = six;
    }

    /**
     * @return the onStrike
     */
    public boolean isOnStrike() {
        return onStrike;
    }

    /**
     * @param onStrike the onStrike to set
     */
    public void setOnStrike(boolean onStrike) {
        this.onStrike = onStrike;
    }
    
    public void incrRuns(Integer runs) {
        this.runs = this.runs + runs;
        balls++;
        if (runs == 4) {
            four++;
        } else if (runs == 6){
            six++;
        }
    }
    
    public void decrRuns(Integer runs) {
        this.runs = this.runs - runs;
        balls--;
        if (runs == 4) {
            four--;
        } else if (runs == 6){
            six--;
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String buffer = "Batsman [ Name " + name + ", Runs: " + runs + ", Balls "
                + balls + ", Dismissed " + out + "]";
        return buffer;
    }
}
