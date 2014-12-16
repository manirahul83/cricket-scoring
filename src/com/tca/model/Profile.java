/**
 * 
 */
package com.tca.model;

/**
 * @author rahumani
 *
 */
public class Profile {
    private String userName;
    private String password;
    private Boolean stayLoggedIn;
    
    public Profile(String userName, String password, Boolean stayLoggedIn) {
        super();
        this.userName = userName;
        this.password = password;
        this.stayLoggedIn = stayLoggedIn;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getStayLoggedIn() {
        return stayLoggedIn;
    }
    public void setStayLoggedIn(Boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}
