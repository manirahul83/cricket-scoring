/**
 * 
 */
package com.tca.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.tca.R;
import com.tca.model.Innings;
import com.tca.model.Match;

/**
 * @author rahumani
 *
 */
public class DetailedScoreActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.detail_score_tab);
        Match match = (Match)getIntent().getSerializableExtra("Match");
        if (match != null) {
            Log.i("Rahul", match.toString());
        }
        Innings innningsTeam1 = match.getInningsById(0);
        Innings inningsTeam2 = match.getInningsById(1);
        TabHost tabHost = getTabHost();
        
        TabSpec team1 = tabHost.newTabSpec(innningsTeam1.getTeamName());
        team1.setIndicator(innningsTeam1.getTeamName());
        Intent team1Intent = new Intent();
        team1Intent.putExtra("Innings", innningsTeam1);
        team1Intent.putExtra(DetailedScoreTeamActivity.TEAMNAME,
                innningsTeam1.getTeamName());
        team1Intent.setClass(this, DetailedScoreTeamActivity.class);
        team1.setContent(team1Intent);
        
        TabSpec team2 = tabHost.newTabSpec(inningsTeam2.getTeamName());
        team2.setIndicator(inningsTeam2.getTeamName());
        Intent team2Intent = new Intent();
        team2Intent.putExtra("Innings", inningsTeam2);
        team2Intent.putExtra(DetailedScoreTeamActivity.TEAMNAME, inningsTeam2.getTeamName());
        team2Intent.setClass(this, DetailedScoreTeamActivity.class);
        team2.setContent(team2Intent);
        
        tabHost.addTab(team1);
        tabHost.addTab(team2);
    }
}
