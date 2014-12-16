/**
 * 
 */
package com.tca.model;

import com.tca.R;

/**
 * @author rahumani
 *
 */
public enum DashboardViewElem {

    Settings(0, "Settings",R.drawable.setting),
    Enter_Brief(1, "Enter Brief Score", R.drawable.cricket),
    View_Brief(2, "View Brief Score", R.drawable.cricket),
    Detail(3, "Detail Scoring", R.drawable.batsman),
    Umpiring(4, "Umpiring Scoring", R.drawable.umpire),
    Leagues(5, "View Leagues", R.drawable.wickets),
    Teams(6, "View Teams", R.drawable.cricket03),
    Players(7, "View Players", R.drawable.cricket01),
    My_Team(8, "My Team", R.drawable.bowling);
    
    private String stringId;
    private int itemNumber;
    private int iconImage;
    
    public String getStringId() {
        return stringId;
    }

    private DashboardViewElem(int position, String typeOfElem, int iconImage) {
        stringId = typeOfElem;
        itemNumber = position;
        this.iconImage = iconImage;
    }
    
    public int getIconImage() {
        return iconImage;
    }

    public static DashboardViewElem getItemByPosition(int position) {
        for (DashboardViewElem element: DashboardViewElem.values()) {
            if (element.getItemNumber() == position) {
                return element;
            }
        }
        return null;
    }

    public int getItemNumber() {
        return itemNumber;
    }
}
