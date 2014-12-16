package com.tca.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tca.R;
import com.tca.model.Profile;
import com.tca.utils.LoginUtil;

public class CricketScoreActivity extends Activity {
    private String userName;
    private String password;
    private DBAdapter dbHelper;
//    ProgressDialog progressDialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        progressDialog = new ProgressDialog(this);
        /**
         * Add code for auto Login
         */
        dbHelper = DBAdapter.getInstance(this);
        Profile userProfile = dbHelper.getUser();
        if (userProfile == null) {
            setContentView(R.layout.main);
            Button loginBtn = (Button) findViewById(R.id.loginBtn);
            loginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    EditText userNameEdit = (EditText) findViewById(R.id.userNameEdit);
                    EditText passwordEdit = (EditText) findViewById(R.id.passwordEdit);
                    CheckBox staySignedIn = (CheckBox) findViewById(R.id.signedInCheckBox);
                    userName = userNameEdit.getText().toString();
                    password = passwordEdit.getText().toString();
                    Profile profile = new Profile(userName, password,
                            staySignedIn.isChecked());
                    dbHelper.addUser(profile);
                    Toast.makeText(getApplicationContext(),
                            userName + " is now Logged in", 5000).show();
                    doLogin(profile);
                }
            });
        } else {
            doLogin(userProfile);
        }
    }
    
    private void doLogin(Profile userProfile) {
        LoginUtil.getInstance().login(userProfile, this);
    }
}