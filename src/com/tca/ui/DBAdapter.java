/**
 * 
 */
package com.tca.ui;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tca.model.Over;
import com.tca.model.Profile;
import com.tca.model.UmpireDetails;

/**
 * @author rahumani
 *
 */
public class DBAdapter {

    private SQLiteDatabase db;
    private DBHelper databaseHelper;
    private static DBAdapter instance;
    public final static String COMMON_USER_DB_NAME = "common_account";
    public final static String UMPIRE_DB_NAME = "umpiring_details";
    private static final String SQL_SEPARATOR = ";";
    public final static int COMMON_USER_DB_VERSION = 1;
    private final String dbFileName = "DBStuff";
    
    private final String FIELD_ROW_ID = "row_id";
    /**
     * User DB Details
     */
    
    private final String FIELD_USERNAME = "username";
    private final String FIELD_PASSWORD = "password";
    private final String FIELD_SIGNEDIN = "staysignedin";
    private final String FIELD_DB_NAME = "user_db_name";
    
    private final Integer FIELD_USERNAME_NDX = 1;
    private final Integer FIELD_PASSWORD_NDX = 2;
    private final Integer FIELD_SIGNEDIN_NDX = 3;
    
    /**
     * Umpiring DB Details
     */
    private final String FIELD_MATCHID = "match_id";
    private final String FIELD_OVERNO = "over_no";
    private final String FIELD_OVERDETAILS = "over_details";
    private final String FIELD_TEAMNAME = "team_name";
    private final String FIELD_BOWLER = "bowler";
    
    private final Integer FIELD_MATCHID_NDX = 1;
    private final Integer FIELD_OVERNO_NDX = 2;
    private final Integer FIELD_OVERDETAILS_NDX = 3;
    private final Integer FIELD_TEAMNAME_NDX = 4;
    private final Integer FIELD_BOWLER_NDX = 5;
    
    private DBAdapter(Context context) {
        databaseHelper = new DBHelper(context);
    }
    
    public static DBAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new DBAdapter(context);
        }
        return instance;
    }

    private class DBHelper extends SQLiteOpenHelper {
        Context myContext;
        
        public DBHelper(Context context) {
            super(context, COMMON_USER_DB_NAME, null, COMMON_USER_DB_VERSION);
            myContext = context;
        }

        
        /**
         * Returns string object from whole InputStream which is mounted to SQL script file inside assets folder
         * Used for getting string object from input stream
         * @param is - input stream
         * @return string representation of file
         * @throws IOException
         */
        public String inputStreamToString(InputStream is) throws IOException {
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                byte b = (byte) result;
                buf.write(b);
                result = bis.read();
            }
            return buf.toString();
        }
        
        
        /*
         * (non-Javadoc)
         * 
         * @see
         * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database
         * .sqlite.SQLiteDatabase)
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            String[] inst = null;
            try {
                inst = inputStreamToString(myContext.getAssets().open(dbFileName)).split(SQL_SEPARATOR);
            } catch(IOException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < inst.length; i++) {
                db.execSQL(inst[i]);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database
         * .sqlite.SQLiteDatabase, int, int)
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // check how to fix upgrade process. In our situation we need to
            // skip this process, because BackUp manager will work here
            db.execSQL("DROP DATABASE IF EXISTS " + COMMON_USER_DB_NAME);
            onCreate(db);
        }
    }
    public void addUser(Profile userProfile) {
        db = databaseHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(FIELD_USERNAME, userProfile.getUserName());
        initialValues.put(FIELD_PASSWORD, userProfile.getPassword());
        if (userProfile.getStayLoggedIn()) {
            initialValues.put(FIELD_SIGNEDIN, 1);
        } else {
            initialValues.put(FIELD_SIGNEDIN, 0);
        }
        db.insert(COMMON_USER_DB_NAME, null, initialValues);
        db.close();
    }
    
    public void updateUser(Profile userProfile) {
        
    }
    
    public boolean deleteUser(String userName) {
        db = databaseHelper.getWritableDatabase();
        boolean result = db.delete(COMMON_USER_DB_NAME, FIELD_USERNAME + "=?",
                new String[] { userName }) > 0;
        db.close();
        return result;
    }
    private Profile convertCursorToProfile(Cursor cursor) {
        
        try {
            String userName = cursor.getString(FIELD_USERNAME_NDX);
            String password = cursor.getString(FIELD_PASSWORD_NDX);
            boolean staySignedIn = cursor.getInt(FIELD_SIGNEDIN_NDX) == 1? true:false;
            Profile profile = new Profile(userName, password, staySignedIn);
            return profile;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Profile getUser() {
        if (db == null) {
            db = databaseHelper.getWritableDatabase();
        }
        if (!db.isOpen()) {
            db = databaseHelper.getReadableDatabase();
        }
        Cursor mCursor = db.query(COMMON_USER_DB_NAME, new String[] {
                FIELD_ROW_ID, FIELD_USERNAME, FIELD_PASSWORD,
                FIELD_SIGNEDIN, FIELD_DB_NAME }, null, null, null, null,
                null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            db.close();
            return convertCursorToProfile(mCursor);
        }
        db.close();
        return null;
    }
    
    public UmpireDetails fetchUmpireDetails(String matchId, String battingTeam) {
        if (db == null) {
            db = databaseHelper.getWritableDatabase();
        }
        if (!db.isOpen()) {
            db = databaseHelper.getReadableDatabase();
        }
        Cursor mCursor = db.query(UMPIRE_DB_NAME, new String[] {
                FIELD_ROW_ID, FIELD_MATCHID, FIELD_OVERNO, FIELD_OVERDETAILS,
                FIELD_TEAMNAME, FIELD_BOWLER }, FIELD_MATCHID + "=? AND "
                + FIELD_TEAMNAME + "=?", new String[] { matchId, battingTeam },
                null, null, null);
        UmpireDetails umpireDetails = null;
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                umpireDetails = new UmpireDetails();
                umpireDetails.setMatchId(mCursor.getString(FIELD_MATCHID_NDX));
                HashMap<Integer, Over> matchDetails = new HashMap<Integer, Over>();
                umpireDetails.setMatchDetails(matchDetails);
                Integer overNo = mCursor.getInt(FIELD_OVERNO_NDX);
                Over over = new Over(overNo,
                        mCursor.getString(FIELD_BOWLER_NDX),
                        mCursor.getString(FIELD_OVERDETAILS_NDX));
                matchDetails.put(overNo, over);
                umpireDetails.setBattingTeam(mCursor
                        .getString(FIELD_TEAMNAME_NDX));
                while (mCursor.moveToNext()) {
                    overNo = mCursor.getInt(FIELD_OVERNO_NDX);
                    Over tempOver = new Over(overNo,
                            mCursor.getString(FIELD_BOWLER_NDX),
                            mCursor.getString(FIELD_OVERDETAILS_NDX));
                    matchDetails.put(overNo, tempOver);
                }
            }
        }
        db.close();
        return umpireDetails;
    }
    
    public void saveUmpireDetails(UmpireDetails umpireDetail) {
        if (db == null || !db.isOpen()) {
            db = databaseHelper.getWritableDatabase();
        }
        
        ContentValues initialValues = new ContentValues();
        Integer overNo = umpireDetail.getCurOverToSave();
        Over over = umpireDetail.getMatchDetails().get(overNo);
        initialValues.put(FIELD_MATCHID, umpireDetail.getMatchId());
        initialValues.put(FIELD_OVERNO, overNo);
        initialValues.put(FIELD_OVERDETAILS, over.getOverDetails());
        initialValues.put(FIELD_TEAMNAME, umpireDetail.getBattingTeam());
        initialValues.put(FIELD_BOWLER, over.getBowler());
        db.insert(UMPIRE_DB_NAME, null, initialValues);
        db.close();
    }
}
