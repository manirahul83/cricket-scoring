/**
 * 
 */
package com.tca.ui;

import java.util.EnumSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.tca.R;
import com.tca.model.DashboardViewElem;
import com.tca.utils.SettingsDialog;

/**
 * @author rahumani
 *
 */
public class DashboardActivity1 extends Activity {
    GridView myGrid;
    Context myContext;
    Toast myToast = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard1);

        myContext = this;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myGrid.setAdapter(null);
        myGrid = null;
        myToast = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myGrid = (GridView) findViewById(R.id.gridView1);
        myGrid.setAdapter(new ImageAdapter(this));

        myGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                if (myToast != null) {
                    myToast.cancel();
                    myToast = null;
                }
                switch (DashboardViewElem.getItemByPosition(position)) {
                case Settings:
                    new SettingsDialog(myContext).show();
                    break;
                case Umpiring:
                    new PreUmpiringDialog(myContext).show();
                    break;
                case Detail:
                    Intent detailIntent = new Intent();
                    detailIntent.setClass(myContext, EnterScoringDetailsActivity.class);
                    myContext.startActivity(detailIntent);
                    break;
                case Enter_Brief:
                case View_Brief:
                case Leagues:
                case My_Team:
                case Players:
                case Teams:
                default:
                    myToast = Toast.makeText(myContext, DashboardViewElem
                            .getItemByPosition(position).getStringId()
                            + " Coming Soon", 5000);
                    myToast.show();
                    break;
                }

            }
        });
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public class ImageAdapter extends BaseAdapter {
        Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return (EnumSet.allOf(DashboardViewElem.class)).size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View MyView = convertView;

            if (convertView == null) {
                /* we define the view that will display on the grid */

                // Inflate the layout
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.grid_item, null);
                DashboardViewElem element = DashboardViewElem.getItemByPosition(position); 
                // Add The Text!!!
                TextView tv = (TextView) MyView
                .findViewById(R.id.grid_item_text);
                tv.setText(element.getStringId());

                // Add The Image!!!
                ImageView iv = (ImageView) MyView
                .findViewById(R.id.grid_item_image);
                iv.setScaleType(ScaleType.CENTER_CROP);
                LayoutParams params = iv.getLayoutParams();
                iv.setImageBitmap(
                        decodeSampledBitmapFromResource(getResources(), element.getIconImage(), params.width, params.height));
            }

            return MyView;
        }
    }
}
