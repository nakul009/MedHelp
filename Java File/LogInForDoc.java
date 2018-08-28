package com.example.nakuld.medhelp99;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by nakulD on 5/16/2018.
 */

public class LogInForDoc extends AppCompatActivity {
    EditText UsernameEt,PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_doc);
        UsernameEt = (EditText) findViewById(R.id.etUserNamefordoc);
        PasswordEt = (EditText) findViewById(R.id.etPasswordfordoc);
    }
    public void OnLogin(View view){
        String username=UsernameEt.getText().toString();
        String password=PasswordEt.getText().toString();
        String type="login";
        validate();
        if (!validate()) {
            Toast.makeText(this, "login failed!!", Toast.LENGTH_SHORT).show();
        }
        else {
            BackgroundWorkerForDoc backgroundWorker = new BackgroundWorkerForDoc(this);
            backgroundWorker.execute(type, username, password);
        }
    }

    private boolean validate() {
        boolean valid = true;
        // String password=password.getText().toString();
        if(UsernameEt.getText().toString().equals(""))
        {
            UsernameEt.setError("enter username");
            //Toast.makeText(this,"username must be of 5 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(PasswordEt.getText().toString().equals(""))
        {
            PasswordEt.setError("enter password");
            //Toast.makeText(this,"password must be of 6 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }

        return valid;
    }

    public void OpenReg(View view){
        startActivity(new Intent(this,RegisterForDoc.class));
    }
}
