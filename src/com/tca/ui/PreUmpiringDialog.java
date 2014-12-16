/**
 * 
 */
package com.tca.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tca.R;

/**
 * @author rahumani
 *
 */
public class PreUmpiringDialog extends Dialog {
    Context context;

    public PreUmpiringDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pre_umpiring);
        this.context = context;
        Button team1 = (Button) findViewById(R.id.team1_btn);
        team1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView team1TV = (TextView) findViewById(R.id.team1_text);
                String team1 = team1TV.getText().toString();

                Intent i = new Intent();
                i.setClass(context, UmpiringActivity.class);
                i.putExtra(UmpiringActivity.INTENT_TEAMNAME, team1);
                i.putExtra(UmpiringActivity.INTENT_MATCHID, "123");
                context.startActivity(i);
                onStop();
            }
        });
        Button team2 = (Button) findViewById(R.id.team2_btn);
        team2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView team2TV = (TextView) findViewById(R.id.team2_text);
                String team2 = team2TV.getText().toString();

                Intent i = new Intent();
                i.setClass(context, UmpiringActivity.class);
                i.putExtra(UmpiringActivity.INTENT_TEAMNAME, team2);
                i.putExtra(UmpiringActivity.INTENT_MATCHID, "123");
                context.startActivity(i);
                onStop();
            }
        });

        TextView team1TV = (TextView) findViewById(R.id.team1_text);
        team1TV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Rahul", "Team 1 has been clicked");

            }
        });
        TextView team2TV = (TextView) findViewById(R.id.team2_text);
        team2TV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("Rahul", "Team 2 has been clicked");

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismiss();
    }
}
