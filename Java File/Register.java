package com.example.nakuld.medhelp99;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dhrumil shah on 2/1/2018.
 */

public class Register extends AppCompatActivity{
    EditText name,surname,email,username,password,confirmpass;
    Button register;
    Boolean abc=false;
    String str_name,str_surname,str_email,str_username,str_password,str_cpassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.etname);
        surname = (EditText) findViewById(R.id.etsurname);
        email = (EditText) findViewById(R.id.etemail);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        confirmpass = (EditText) findViewById(R.id.cetpassword);
        register = (Button) findViewById(R.id.btn_reg);

    }
    public void OnReg(View view) {

        String str_name = name.getText().toString();
        String str_surname = surname.getText().toString();
        String str_email = email.getText().toString();
        String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String str_cpassword = confirmpass.getText().toString();
        String type = "Register";
        //String validateemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //Matcher matcher=Pattern.compile(validateemail).matcher(str_email);
        validate();
        if (!validate()) {
            Toast.makeText(this, "register failed!!", Toast.LENGTH_SHORT).show();
        } else {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, str_name, str_surname, str_email, str_username, str_password, str_cpassword);
        }
    }

    private boolean validate() {
        String cpass=confirmpass.getText().toString();
        String pass=password.getText().toString();
        String validemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emaill=email.getText().toString();
        Matcher matcher= Pattern.compile(validemail).matcher(emaill);
        boolean valid=true;
        if(name.getText().toString().equals(""))
        {
            name.setError("name must be of 4 characters ");
            //Toast.makeText(this,"Name must be of 4 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else if(surname.getText().toString().equals(""))
        {
            surname.setError("surname must be of 4 characters");
            //Toast.makeText(this,"surname must be of 4 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }

        else if(!matcher.matches())
        {
            email.setError("Invalid email");
            //Toast.makeText(this,"Invalid email",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else if(username.getText().toString().length()<3)
        {
            username.setError("username must be of 5 characters");
            //Toast.makeText(this,"username must be of 5 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else if(pass.length() < 5)
        {
            password.setError("password atleast be of 6 characters");
            //Toast.makeText(this,"password must be of 6 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        // else if(confirmpass.getText().toString().equals(""))
        else if(!pass.equals(cpass))
        {
            confirmpass.setError("password and confirm password not match");
            //Toast.makeText(this,"password and confirm password not match",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        return valid;
        // if(abc)
        //{
        //  Intent intent=new Intent(register.this,MainActivity.class);
        //startActivity(intent);

        //}
    }


}

