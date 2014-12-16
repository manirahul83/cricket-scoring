/**
 * 
 */
package com.tca.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.tca.R;
import com.tca.model.Batsman;
import com.tca.model.Bowler;
import com.tca.model.Extras;
import com.tca.model.FOW;
import com.tca.model.Innings;
import com.tca.model.Match;
import com.tca.model.Over;
import com.tca.model.Score;
import com.tca.model.UmpireDetails;
import com.tca.utils.ScoreHelper;

/**
 * @author rahumani
 * 
 */
public class EnterScoringDetailsActivity extends Activity {

    private Context context;
    private Innings innings;
    private Match match;
    private Batsman bat1;
    private Batsman bat2;
    private Bowler bowler1;
    private Bowler bowler2;
    private ListView battingList;
    private ListView bowlingList;
    private ListView scoringList;
    private UmpireDetails umpDetail;
    private List<String> scorePerOver = new ArrayList<String>(1);
    private Integer overNo = 1;
    private EditText curOverEdit;
    private Button enterOver;
    private boolean keyboardShown;
    private InputMethodManager imm;
    private String battingTeam;
    private StatAdapter bowlingAdapter;
    private StatAdapter battingAdapter;
    private boolean changeStrike = false;
    private ScoreAdapter scoringAdapter = new ScoreAdapter();
    private boolean newOver = false;
    private String beforeTextChanged;

     private String matchID = "try123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_scoring);
        this.imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        context = this;
        
        curOverEdit = (EditText) findViewById(R.id.enter_scoring_cur_over);
        curOverEdit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    keyboardShown = true;
                }
                return false;
            }
        });
        
        curOverEdit.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                Log.i("Rahul", "Key Code "+keyCode);
                Log.i("Rahul", "Key Event "+event);
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == 0) {
                    // this is for backspace
                    String curOver = curOverEdit.getText().toString();
                    String[] test = curOver.split(",");
                    String ball = test[test.length - 1];
                    Log.i("Rahul", "Last Ball is " + ball);
                    Log.i("Rahul", "Display is " + curOver);
                    String replaceText = "";
                    for (int count = 0; count < test.length - 1; count++) {
                        replaceText = replaceText + test[count] + ",";
                    }
                    if (ball.contains("+")) {
                        ball = ball.substring(0, ball.indexOf("+"));
                        replaceText = replaceText + ball;
                    }
                    Log.i("Rahul", "Replace Text is "+replaceText);
                    curOverEdit.setText(replaceText);
                    curOverEdit.setSelection(replaceText.length());
                    return true;
                }
                return false;
            }
        });
        curOverEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (newOver) {
                    newOver = false;
                    String temp = beforeTextChanged.toString();
                    String[] test = temp.split(",");
                    Score score = getBallDetail(test[test.length - 1]);
                    Log.i("Rahul", "Score is " + score.toString());
                    if (score != null) {
                        updateScore(score, false);
                    }
                    changeStrike = true;
                    checkAndChangeStrike();
                    return;
                }
                Log.i("Rahul", "OnText is " + s);
                String onTextChanged = s.toString();
                if (onTextChanged.length() > beforeTextChanged.length()) {
                    String ball = s.toString().substring(start, start + count);
                    if (ball.equalsIgnoreCase(",")) {
                        String temp = s.toString();
                        String[] test = temp.split(",");
                        Score score = getBallDetail(test[test.length - 1]);
                        Log.i("Rahul", "Score is " + score.toString());
                        if (score != null) {
                            updateScore(score, false);
                        }
                    }
                } else {
                    if (beforeTextChanged.contains(",")) {
                        if (beforeTextChanged.substring(beforeTextChanged.length() - 1).contains(",")) {
                            String[] test = beforeTextChanged.split(",");
                            Log.i("Rahul", "Display is " + test[test.length - 1]);
                            Score score = getBallDetail(test[test.length - 1]);
                            if (score != null) {
                                updateScore(score, true);
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                Log.i("Rahul", "BEFOREText is " + s);
                beforeTextChanged = s.toString();
            }
        });
        enterOver = (Button) findViewById(R.id.enter_scoring_save);
        enterOver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (umpDetail.getMatchDetails() == null) {
                    umpDetail.setMatchDetails(new HashMap<Integer, Over>());
                    umpDetail.setBattingTeam(battingTeam);
                }
                Score score = ScoreHelper.getInstance().getOverRuns(
                        curOverEdit.getText().toString());
                if (score.getRuns() == 0
                        || (score.getRuns() > 0 && score.getExtras().getByes()
                                .equals(score.getRuns().intValue()))) {
                    Log.i("Rahul", "Maiden Over");
                    bowler1.setMaidens(bowler1.getMaidens() + 1);
                }
                Over over = new Over(overNo, bowler1.getName(), curOverEdit
                        .getText().toString());
                bowler1.getOvers().put(overNo, over);
                umpDetail.getMatchDetails().put(overNo, over);
                umpDetail.setCurOverToSave(overNo);
                
                scoringList.setAdapter(new ScoreAdapter());
                newOver = true;
                curOverEdit.setText("");
                overNo++;
                getNewBowler();
                /*
                 * Need to add code for getting next Bowler
                 */
                bowlingAdapter.notifyDataSetChanged();
                showSoftKeyboard(false);
            }
        });
        Button viewScore = (Button)findViewById(R.id.view_score);
        viewScore.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent();
                detailIntent.putExtra("Match", match);
                detailIntent.setClass(context, DetailedScoreActivity.class);
                context.startActivity(detailIntent);
                
            }
        });
        Button nextInnings = (Button)findViewById(R.id.next_inn);
        nextInnings.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
            }
        });
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
    protected void onPause() {
        super.onPause();
        battingList.setAdapter(null);
        bowlingList.setAdapter(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        match = new Match(matchID, "India", "Australia");
        innings = match.getInningsById(0);
        
        bat1 = innings.newBatsman("Rahul");
        bat2 = innings.newBatsman("Abc");
        bat1.setOnStrike(true);
        battingList = (ListView) findViewById(R.id.enter_scoring_bat);
        battingAdapter = new StatAdapter();
        battingAdapter.addItem("Batting Header");
        battingAdapter.addItem(bat1);
        battingAdapter.addItem(bat2);
        battingList.setAdapter(battingAdapter);

        bowler1 = innings.newBowler("XYZ");
        bowlingList = (ListView) findViewById(R.id.enter_scoring_bowl);
        bowlingAdapter = new StatAdapter();
        bowlingAdapter.addItem("Bowling Header");
        bowlingAdapter.addItem(bowler1);
        bowlingList.setAdapter(bowlingAdapter);
        
        scoringList = (ListView) findViewById(R.id.enter_scoring_list);
        if (umpDetail != null) {
            if (umpDetail.getMatchDetails() != null) {
                Collection<Over> matchDetails = umpDetail.getMatchDetails()
                        .values();
                if (matchDetails != null && !matchDetails.isEmpty()) {
                    overNo = matchDetails.size() + 1;
                }
                scoringList.setAdapter(scoringAdapter);
            }
        } else {
            umpDetail = new UmpireDetails();
            umpDetail.setMatchId("try");
            scoringList.setAdapter(scoringAdapter);
        }
    }

    private Score getBallDetail(String ball) {
        Log.i("Rahul", "Text is " + ball);
        Score score = null;
        score = ScoreHelper.getInstance().analyzeBall(ball);
        if (score == null) {
            score = new Score();
        }
        return score;
    }

    private void getNewBowler() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setTitle("Next Over");
        myDialog.setContentView(R.layout.bowling_change);
        android.view.WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        myDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        
        CheckBox tv = (CheckBox)myDialog.findViewById(R.id.previous_bowler);
        if (bowler2 != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(bowler2.getName());
        } else {
            tv.setVisibility(View.INVISIBLE);
        }
        tv.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Bowler tempBowler = bowler2;
                    bowler2 = bowler1;
                    bowlingAdapter.removeItem(bowler1);
                    bowler1 = tempBowler;
                    bowlingAdapter.addItem(bowler1);
                    bowlingAdapter.notifyDataSetChanged();
                    myDialog.cancel();
                }
            }
        });
        Button newBowlerDone = (Button) myDialog.findViewById(R.id.new_bowler_done);
        newBowlerDone.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText newBowler = (EditText) myDialog.findViewById(R.id.new_bowler);
                bowler2 = bowler1;
                bowlingAdapter.removeItem(bowler1);
                bowler1 = innings.newBowler(newBowler.getText().toString());
                bowlingAdapter.addItem(bowler1);
                bowlingAdapter.notifyDataSetChanged();
                myDialog.cancel();
            }
        });
        myDialog.show();
    }
    private void getWicketDetails() {
        final Dialog myDialog = new Dialog(this);
        myDialog.setTitle("Howzatt!!!");
        myDialog.setContentView(R.layout.howzatt);
        android.view.WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        myDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        CheckBox chkBox1 = ((CheckBox)myDialog.findViewById(R.id.checkBox1));
        CheckBox chkBox2 = ((CheckBox)myDialog.findViewById(R.id.checkBox2));
        chkBox1.setText(bat1.getName());
        chkBox2.setText(bat2.getName());
        Button btnOk = (Button)myDialog.findViewById(R.id.out_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                boolean bat1Out = ((CheckBox)myDialog.findViewById(R.id.checkBox1)).isChecked();
                boolean bat2Out = ((CheckBox)myDialog.findViewById(R.id.checkBox2)).isChecked();
                EditText howOut = (EditText)myDialog.findViewById(R.id.how_out);
                EditText newBat = (EditText)myDialog.findViewById(R.id.new_bat);
                if (bat1Out) {
                  Log.i("Rahul", "Batsman out "+bat1.getName());
                  FOW fow = new FOW();
                  fow.setBatsmanName(bat1.getName());
                  fow.setScore(innings.getScore());
                  innings.addFallOfWickets(fow);
                  bat1.setOut(howOut.getText().toString());
                  Log.i("Rahul", bat1.toString());
                  battingAdapter.removeItem(bat1);
                  
                  bat1 = innings.newBatsman(newBat.getText().toString());
                  battingAdapter.addItem(bat1);
                  bat1.setOnStrike(true);
                }
                if (bat2Out) {
                  Log.i("Rahul", "Batsman out "+bat2.getName());
                  FOW fow = new FOW();
                  fow.setBatsmanName(bat2.getName());
                  fow.setScore(innings.getScore());
                  innings.addFallOfWickets(fow);
                  bat2.setOut(howOut.getText().toString());
                  Log.i("Rahul", bat2.toString());
                  battingAdapter.removeItem(bat2);
                  bat2 = innings.newBatsman(newBat.getText().toString());
                  battingAdapter.addItem(bat2);
                  bat2.setOnStrike(true);
                }
                battingAdapter.notifyDataSetChanged();
                myDialog.cancel();
            }
        });
        myDialog.show();
    }
    private void updateScore(Score score, boolean undoPrev) {
        TextView scoreDisplay = (TextView)findViewById(R.id.score_display);
        TextView overDisplay = (TextView)findViewById(R.id.over_display);
        innings.updateScore(score, undoPrev);
        if (score.getWickets() > 0) {
            Log.i("Rahul", "Wicket Falling");
            /*
             * Add code for adding details on who got out.
             */
            getWicketDetails();
            if (undoPrev) {
                bowler1.decrWickets();
            } else {
                bowler1.incrWickets();
            }
        }
        changeStrike = false;
        Extras extra = score.getExtras();
        Integer runsByBatsman = score.getRuns() - extra.getWides()
        - extra.getNoBall() - extra.getByes();
        Integer runsForBowler = score.getRuns() - extra.getByes();
        /*
         * Update Batsman Details
         */
        if (runsByBatsman % 2 != 0) {
            changeStrike = true;
        }
        if (bat1.isOnStrike()) {
            if (undoPrev) {
                if (changeStrike) {
                    bat2.decrRuns(runsByBatsman);
                } else {
                    bat1.decrRuns(runsByBatsman);
                }
            } else {
                bat1.incrRuns(runsByBatsman);
            }
        } else {
            if (undoPrev) {
                if (changeStrike) {
                    bat1.decrRuns(runsByBatsman);
                } else {
                    bat2.decrRuns(runsByBatsman);
                }
            } else {
                bat2.incrRuns(runsByBatsman);
            }
        }
        checkAndChangeStrike();
        /*
         * Update Bowler Details
         */
        if (undoPrev) {
            bowler1.decrRuns(runsForBowler);
            bowler1.decrWides(score.getExtras().getWides());
            bowler1.decrNoBall(score.getExtras().getNoBall());
        } else {
            bowler1.incrRuns(runsForBowler);
            bowler1.incrWides(score.getExtras().getWides());
            bowler1.incrNoBall(score.getExtras().getNoBall());
        }
        if (score.getBallCount() > 0) {
            if (undoPrev) {
                bowler1.decrBall();
            } else {
                bowler1.incrBall();
            }
        }
        /*
         * Update Match Details.
         */
        overDisplay.setText(bowler1.getDisplayOvers());
        Score innScore = innings.getScore();
        scoreDisplay.setText(innScore.getRuns() + "/" +innScore.getWickets());
        if (score.getWickets() > 0 || score.getRuns() > 0) {
            bowlingAdapter.notifyDataSetChanged();
            battingAdapter.notifyDataSetChanged();
        }
    }
    
    private void checkAndChangeStrike() {
        if (changeStrike) {
            changeStrike = false;
            if (bat1.isOnStrike()) {
                bat2.setOnStrike(true);
                bat1.setOnStrike(false);
            } else {
                bat1.setOnStrike(true);
                bat2.setOnStrike(false);
            }
        }
    }

    private void showSoftKeyboard(boolean show) {
        if (this.imm != null) {
            if (show) {
                this.imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                keyboardShown = true;
            } else {
                this.imm.hideSoftInputFromWindow(this.getWindow()
                        .getDecorView().getWindowToken(), 0);
                keyboardShown = false;
            }
        }
    }

    public class ScoreAdapter extends BaseAdapter {

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View MyView = convertView;
            OverViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.umpireoverdetails, null);
                holder = new OverViewHolder();
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
                holder = (OverViewHolder) convertView.getTag();
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
            String score = ScoreHelper.getInstance().getScore(
                    (position == 0) ? null : scorePerOver.get(position - 1),
                    over.getOverDetails());
            scorePerOver.add(position, score);
            holder.overScore.setText(score);
            return MyView;

        }

    }

    public class OverViewHolder {
        public TextView overNo;
        public TextView bowler;
        public TextView overScore;
        public TextView overDetail;
    }

    public class StatAdapter extends BaseAdapter {
        private List<Object> scoreData;

        public StatAdapter() {
            scoreData = new ArrayList<Object>();
        }

        public void addItem(Object data) {
            scoreData.add(data);
        }
        
        public void removeItem(Object data) {
            scoreData.remove(data);
        }

        @Override
        public int getCount() {
            return scoreData.size();
        }

        @Override
        public Object getItem(int position) {
            return scoreData.get(position);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater li = getLayoutInflater();
                myView = li.inflate(R.layout.enter_scoring_details, null);
                holder = new ViewHolder();
                holder.nameText = (TextView) myView
                        .findViewById(R.id.detail_score_bowler);
                holder.runsText = (TextView) myView
                        .findViewById(R.id.detail_bowl_r);
                holder.overText = (TextView) myView
                        .findViewById(R.id.detail_bowl_o);
                holder.maidenText = (TextView) myView
                        .findViewById(R.id.detail_bowl_m);
                holder.wicketText = (TextView) myView
                        .findViewById(R.id.detail_bowl_w);
                myView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (getItem(position) instanceof Batsman) {
                Batsman batsman = (Batsman) getItem(position);
                String batsmanName = batsman.getName();
                if (batsman.isOnStrike()) {
                    batsmanName = batsmanName + "*";
                }
                holder.nameText.setText(batsmanName);
                holder.overText.setText(String.valueOf(batsman.getRuns()));
                holder.maidenText.setText(String.valueOf(batsman.getBalls()));
                holder.runsText.setText(String.valueOf(batsman.getFour()));
                holder.wicketText.setText(String.valueOf(batsman.getSix()));
            } else if (getItem(position) instanceof Bowler) {
                Bowler bowler = (Bowler) getItem(position);

                holder.nameText.setText(bowler.getName());
                holder.runsText.setText(String.valueOf(bowler.getRuns()));
                holder.overText.setText(String.valueOf(bowler.getDisplayOvers()));
                holder.maidenText.setText(String.valueOf(bowler.getMaidens()));
                holder.wicketText.setText(String.valueOf(bowler.getWickets()));
            } else {
                String temp = (String) getItem(position);
                if (temp.equalsIgnoreCase("Batting Header")) {
                    holder.nameText.setText("Batting");
                    holder.overText.setText("R");
                    holder.maidenText.setText("B");
                    holder.runsText.setText("4s");
                    holder.wicketText.setText("6s");
                } else if (temp.equalsIgnoreCase("Bowling Header")) {
                    holder.nameText.setText("Bowling");
                    holder.runsText.setText("R");
                    holder.overText.setText("O");
                    holder.maidenText.setText("M");
                    holder.wicketText.setText("W");
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
    }
}
