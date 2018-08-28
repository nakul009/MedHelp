package com.example.nakuld.medhelp99;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nakulD on 5/16/2018.
 */

public class RegisterForDoc extends AppCompatActivity{
    EditText city,email,username,password,confirmpass,PhoneNo;
    Button register;
    Boolean abc = false;
    String str_email,str_username,str_password,str_cpassword,str_city;
    String str_phonenoofstring;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_doc);
        username = (EditText) findViewById(R.id.etusername);
        city = (EditText) findViewById(R.id.etcityname);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);
        confirmpass = (EditText) findViewById(R.id.cetpassword);
        register = (Button) findViewById(R.id.btn_reg);
        PhoneNo = (EditText) findViewById(R.id.phoneno);
    }
    public void OnReg(View view) {

        String str_username = username.getText().toString();
        String str_city = city.getText().toString();
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        String str_cpassword = confirmpass.getText().toString();
        str_phonenoofstring = PhoneNo.getText().toString();
        String type = "Register";
        //String validateemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //Matcher matcher=Pattern.compile(validateemail).matcher(str_email);
        validate();
        if (!validate()) {
            Toast.makeText(this, "register failed!!", Toast.LENGTH_SHORT).show();
        } else {
            BackgroundWorkerForDoc backgroundWorker = new BackgroundWorkerForDoc(this);
            backgroundWorker.execute(type, str_username, str_city, str_email, str_password, str_cpassword,str_phonenoofstring);
        }
    }

    private boolean validate() {
        String cpass=confirmpass.getText().toString();
        String pass=password.getText().toString();
        String MobilePattern = "[0-9]{10}";
        String number = PhoneNo.getText().toString();
        String validemail="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emaill=email.getText().toString();
        Matcher matcher= Pattern.compile(validemail).matcher(emaill);
        boolean valid=true;
        if(username.getText().toString().equals(""))
        {
            username.setError("name must be of 4 characters ");
            //Toast.makeText(this,"Name must be of 4 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else if(city.getText().toString().equals(""))
        {
            city.setError("city must be of 4 characters");
            //Toast.makeText(this,"surname must be of 4 characters",Toast.LENGTH_SHORT).show();
            valid=false;
        }

        else if(!matcher.matches())
        {
            email.setError("Invalid email");
            //Toast.makeText(this,"Invalid email",Toast.LENGTH_SHORT).show();
         //   valid=false;
        }

        else if(password.getText().toString().equals(""))
        {
            password.setError("password must be of 6 characters");
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
        else if(PhoneNo.getText().toString().matches(MobilePattern)) {

            Toast.makeText(getApplicationContext(), "phone number is valid", Toast.LENGTH_SHORT).show();
            return true;

        } else if(!PhoneNo.getText().toString().matches(MobilePattern)) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return valid;
        // if(abc)
        //{
        //  Intent intent=new Intent(register.this,MainActivity.class);
        //startActivity(intent);

        //}
    }
}
