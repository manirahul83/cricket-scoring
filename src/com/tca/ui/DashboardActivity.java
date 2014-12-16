/**
 * 
 */
package com.tca.ui;

import java.util.EnumSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tca.R;
import com.tca.model.DashboardViewElem;
import com.tca.utils.SettingsDialog;

/**
 * @author rahumani
 *
 */
public class DashboardActivity extends Activity {
    private ListView table;
    private Context context;
    private Toast myToast = null;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        table.setAdapter(null);
        table = null;
        myToast = null;
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        table = (ListView) findViewById(R.id.listView1);

        table.setAdapter(new RowAdapter(this));
        table.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                if (myToast != null) {
                    myToast.cancel();
                    myToast = null;
                }
                switch (DashboardViewElem.getItemByPosition(position)) {
                case Settings:
                    new SettingsDialog(context).show();
                    break;
                case Umpiring:
                    new PreUmpiringDialog(context).show();
                    break;
                case Enter_Brief:
                case View_Brief:
                case Detail:
                    Intent detailIntent = new Intent();
                    detailIntent.setClass(context, DetailedScoreActivity.class);
                    context.startActivity(detailIntent);
                    break;
                case Leagues:
                case My_Team:
                case Players:
                case Teams:
                default:
                    myToast = Toast.makeText(context, DashboardViewElem
                            .getItemByPosition(position).getStringId()
                            + " Coming Soon", 5000);
                    myToast.show();
                    break;
                }
            }
        });
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        context = this;
    }
    
    public class RowAdapter extends BaseAdapter {
        Context context;
        
        public RowAdapter(Context context) {
            this.context = context;
        }
        
        @Override
        public int getCount() {
            return EnumSet.allOf(DashboardViewElem.class).size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;
            
            if (convertView == null) {
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.row_item, null);
                DashboardViewElem element = DashboardViewElem
                        .getItemByPosition(position);
                ImageView iv = (ImageView) MyView.findViewById(R.id.row_item_image);
                iv.setImageResource(android.R.drawable.divider_horizontal_bright);
                
                TextView tv = (TextView) MyView.findViewById(R.id.row_item_text);
                tv.setText(element.getStringId());
                tv.setTextAppearance(context, android.R.attr.textAppearanceLarge);
                
            }
            return MyView;
        }
        
    }
}
