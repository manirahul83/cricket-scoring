/**
 * 
 */
package com.tca.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tca.R;
import com.tca.model.Batsman;
import com.tca.model.Bowler;
import com.tca.model.Innings;

/**
 * @author rahumani
 *
 */
public class DetailedScoreTeamActivity extends Activity {
    public static final String TEAMNAME = "TEAMNAME";
    private ListView detailScoreLayout;   
    private String teamName;
    private Innings innings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_score);
        teamName = getIntent().getExtras().getString(TEAMNAME);
        innings = (Innings)getIntent().getSerializableExtra("Innings");
        if (innings != null) {
            Log.i("Rahul", innings.toString());
        }
        Log.i("Rahul", "Team Name is "+teamName);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        detailScoreLayout = (ListView)findViewById(R.id.detail_list1);
        int count;
        List<Batsman> batsmans = innings.getBatsmans();
        List<Bowler> bowlers = innings.getBowlers();
        BattingAdapter mAdapter = new BattingAdapter();
        mAdapter.addItem("Batting Header");
        for (count = 0; count <batsmans.size(); count++) {
            Batsman batsman = batsmans.get(count);
            mAdapter.addItem(batsman);
        }
        mAdapter.addItem("Bowling Header");

        for (count = 0; count < bowlers.size(); count++) {
            Bowler bowler = bowlers.get(count);
            mAdapter.addItem(bowler);
        }
        detailScoreLayout.setAdapter(mAdapter);
    }

    public class BattingAdapter extends BaseAdapter {
        private List<Object> scoreData = new ArrayList<Object>();

        public void addItem(Object data) {
            scoreData.add(data);
        }
        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            return scoreData.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            return scoreData.get(position);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater li = getLayoutInflater();
                myView = li.inflate(R.layout.detail_score_bowl, null);
                holder = new ViewHolder();
                holder.nameText = (TextView) myView.findViewById(R.id.detail_score_bowler);
                holder.runsText = (TextView) myView.findViewById(R.id.detail_bowl_r);
                holder.overText = (TextView) myView.findViewById(R.id.detail_bowl_o);
                holder.maidenText = (TextView) myView.findViewById(R.id.detail_bowl_m);
                holder.wicketText = (TextView) myView.findViewById(R.id.detail_bowl_w);
                holder.wideText = (TextView) myView.findViewById(R.id.detail_bowl_wd);
                holder.noballText = (TextView) myView.findViewById(R.id.detail_bowl_nb);
                holder.howOutText = (TextView) myView.findViewById(R.id.detail_score_out);
                myView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            if (position % 2 == 0) {
                myView.setBackgroundColor(Color.DKGRAY);
            } else {
                myView.setBackgroundColor(Color.BLACK);
            }
            if (getItem(position) instanceof Batsman) {
                Batsman batsman = (Batsman) getItem(position);
                holder.nameText.setText(batsman.getName());
                holder.overText.setText("");
                holder.maidenText.setText("");
                holder.runsText.setText(String.valueOf(batsman.getRuns()));
                holder.wicketText.setText(String.valueOf(batsman.getBalls()));
                holder.wideText.setText(String.valueOf(batsman.getFour()));
                holder.noballText.setText(String.valueOf(batsman.getSix()));
                holder.howOutText.setText(batsman.getOut());
            } else if (getItem(position) instanceof Bowler) {
                Bowler bowler = (Bowler) getItem(position);
                holder.nameText.setText(bowler.getName());
                holder.runsText.setText(String.valueOf(bowler.getRuns()));
                holder.overText.setText(String.valueOf(bowler.getDisplayOvers()));
                holder.maidenText.setText(String.valueOf(bowler.getMaidens()));
                holder.wicketText.setText(String.valueOf(bowler.getWickets()));
                holder.wideText.setText(String.valueOf(bowler.getWides()));
                holder.noballText.setText(String.valueOf(bowler.getNoball()));
                holder.howOutText.setText("");
            } else {
                String temp = (String) getItem(position);
                if (temp.equalsIgnoreCase("Batting Header")) {
                    holder.nameText.setText(teamName);
                    holder.overText.setText("");
                    holder.maidenText.setText("");
                    holder.runsText.setText("R");
                    holder.wicketText.setText("B");
                    holder.wideText.setText("4s");
                    holder.noballText.setText("6s");
                    holder.howOutText.setText("");
                } else if (temp.equalsIgnoreCase("Bowling Header")) {
                    holder.nameText.setText("Bowling");
                    holder.runsText.setText("R");
                    holder.overText.setText("O");
                    holder.maidenText.setText("M");
                    holder.wicketText.setText("W");
                    holder.wideText.setText("Wd");
                    holder.noballText.setText("Nb");
                    holder.howOutText.setText("");
                }
            }
            return myView;
        }

    }
    public class ViewHolder {
        public TextView nameText;
        public TextView runsText;
        public TextView overText;
        public TextView maidenText;
        public TextView wicketText;
        public TextView wideText;
        public TextView noballText;
        public TextView howOutText;
    }
}
