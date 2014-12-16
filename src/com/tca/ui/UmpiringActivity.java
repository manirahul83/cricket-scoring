/**
 * 
 */
package com.tca.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tca.R;
import com.tca.model.Over;
import com.tca.model.UmpireDetails;
import com.tca.utils.ScoreHelper;

/**
 * @author rahumani
 *
 */
public class UmpiringActivity extends Activity {

    public static final String INTENT_TEAMNAME = "TEAM_NAME";
    public static final String INTENT_MATCHID = "MATCHID";
    private Button enterOver;
    private ListView overDetails;
    private List<String> scorePerOver = new ArrayList<String>(1);
    private UmpireDetails umpDetail;
    private Integer overNo = 1;
    private Context context;
    private EditText curOverEdit;
    private boolean keyboardShown;
    private InputMethodManager imm;
    private Toast myToast = null;
    private String battingTeam;
    private String matchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umpiring);
        context = this;
        
        this.imm = (InputMethodManager) this
        .getSystemService(Context.INPUT_METHOD_SERVICE);
        battingTeam = getIntent().getStringExtra(INTENT_TEAMNAME);
        matchID = getIntent().getStringExtra(INTENT_MATCHID);
        TextView tv = (TextView)findViewById(R.id.umpire_team_bat);
        tv.setText(battingTeam);
        curOverEdit = (EditText)findViewById(R.id.umpire_curr_over);
        curOverEdit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    keyboardShown = true;
                }
                return false;
            }
        });
        curOverEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (myToast != null) {
                    myToast.cancel();
                    myToast = null;
                }
                String tempOver = s.toString();
                if (tempOver.contains(",")) {
                    String delivery = tempOver.substring(tempOver
                            .lastIndexOf(",") + 1);
                    if (delivery.matches("^[0-9 ]+$")) {
                        Integer tempRun = Integer.parseInt(delivery);
                        if (tempRun == 5 || tempRun > 6) {
                            myToast = Toast.makeText(context, "Invalid Run Entered", 5000);
                            myToast.show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                    int arg2, int arg3) {
                // TODO Auto-generated method stub

            }
        });
        enterOver = (Button) findViewById(R.id.umpire_over_btn);
        enterOver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (umpDetail.getMatchDetails() == null) {
                    umpDetail.setMatchDetails(new HashMap<Integer, Over>());
                }
                umpDetail.getMatchDetails().put(overNo, new Over(overNo, "Rahul", curOverEdit.getText().toString() ));
                umpDetail.setCurOverToSave(overNo);
                umpDetail.setBattingTeam(battingTeam);
                DBAdapter.getInstance(context).saveUmpireDetails(umpDetail);
                overDetails.setAdapter(new OverAdapter());
                curOverEdit.setText("");
                overNo++;
                showSoftKeyboard(false);
            }
        });
    }

    private void showSoftKeyboard(boolean show) {
        if(this.imm != null) {
            if(show) {
                this.imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                keyboardShown = true;
            } else {
                this.imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
                keyboardShown = false;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overDetails.setAdapter(null);
        if (umpDetail != null) {
            if (umpDetail.getMatchDetails() != null) {
                umpDetail.getMatchDetails().clear();
            }
            umpDetail = null;
        }
        scorePerOver.clear();
    }

    @Override
    public void onBackPressed() {
        if (keyboardShown) {
            showSoftKeyboard(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overDetails = (ListView) findViewById(R.id.umpire_list);
        umpDetail = DBAdapter.getInstance(context).fetchUmpireDetails(matchID,
                battingTeam);
        if (umpDetail != null) {
            Log.i("Rahul", "Details "+umpDetail.toString());
            Collection<Over> matchDetails = umpDetail.getMatchDetails().values();
            if (matchDetails != null && !matchDetails.isEmpty()) {
                overDetails.setAdapter(new OverAdapter());
                overNo = matchDetails.size() + 1;
            }
        } else {
            umpDetail = new UmpireDetails();
            umpDetail.setMatchId(matchID);
        }
    }
    public class OverAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return umpDetail.getMatchDetails().size();
        }

        @Override
        public Object getItem(int position) {
            return umpDetail.getMatchDetails().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.umpireoverdetails, null);
                holder = new ViewHolder();
                holder.overNo = (TextView) MyView
                        .findViewById(R.id.umpire_over_no);
                holder.overDetail = (TextView) MyView
                        .findViewById(R.id.umpire_over_detail);
                holder.overScore = (TextView) MyView
                        .findViewById(R.id.umpire_over_score);
                holder.bowler = (TextView) MyView
                        .findViewById(R.id.umpire_bowler);
                MyView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            if (position % 2 == 0) {
                MyView.setBackgroundColor(Color.DKGRAY);
            } else {
                MyView.setBackgroundColor(Color.BLACK);
            }
            int overNo = position + 1;
            Over over = umpDetail.getMatchDetails().get(overNo);
            holder.overNo.setText("Over # " + over.getOverNo());
            holder.overDetail.setText(over.getOverDetails());
            holder.bowler.setText(over.getBowler());
            String score = ScoreHelper.getInstance().getScore((position == 0) ? null : scorePerOver
                    .get(position - 1), over.getOverDetails()); 
            scorePerOver.add(position, score);
            holder.overScore.setText(score);
            return MyView;
        }
    }

    public class ViewHolder {
        public TextView overNo;
        public TextView bowler;
        public TextView overScore;
        public TextView overDetail;
    }
}
