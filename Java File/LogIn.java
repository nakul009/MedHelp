package com.example.nakuld.medhelp99;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by nakulD on 4/19/2018.
 */

public class LogIn extends AppCompatActivity{
        EditText UsernameEt,PasswordEt;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            UsernameEt = (EditText) findViewById(R.id.etUserName);
            PasswordEt = (EditText) findViewById(R.id.etPassword);
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
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
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
            startActivity(new Intent(this,Register.class));
        }

    public void OpenRegfordoc(View view) {
        startActivity(new Intent(this,RegisterForDoc.class));
    }

    public void OpenLogInfordoc(View view) {
        startActivity(new Intent(this,LogInForDoc.class));
    }
}
