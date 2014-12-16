/**
 * 
 */
package com.tca.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tca.R;
import com.tca.model.Profile;
import com.tca.ui.DBAdapter;

/**
 * @author rahumani
 *
 */
public class SettingsDialog extends Dialog {

    EditText userNameEdit;
    EditText passwordEdit;
    CheckBox signedInEdit;
    Profile profile;
    DBAdapter db;
    
    public SettingsDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);
        db = DBAdapter.getInstance(context);
        profile = db.getUser();
        userNameEdit = (EditText)findViewById(R.id.settingsUserNameEdit);
        passwordEdit = (EditText)findViewById(R.id.settingsPasswordEdit);
        signedInEdit = (CheckBox)findViewById(R.id.settingsSignedInCheckBox);
        if (profile != null) {
            userNameEdit.setText(profile.getUserName());
            passwordEdit.setText(profile.getPassword());
            signedInEdit.setChecked(profile.getStayLoggedIn());
        }
        Button ok = (Button)findViewById(R.id.settingsOk);
        ok.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                Boolean staySignedIn = signedInEdit.isChecked();
                Profile userProfile = new Profile(userName, password, staySignedIn);
                db.deleteUser(profile.getUserName());
                db.addUser(userProfile);
                dismiss();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        dismiss();
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
