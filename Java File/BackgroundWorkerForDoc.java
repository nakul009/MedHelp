package com.example.nakuld.medhelp99;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nakulD on 5/16/2018.
 */

public class BackgroundWorkerForDoc extends AsyncTask<String, Void, String>{

    Context context;
    String x;
    AlertDialog alertDialog;
    BackgroundWorkerForDoc(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        //String type = params[0];
        x=params[0];
        String login_url = "https://medhelp009.000webhostapp.com//dlogin.php";
        String Register_url = "https://medhelp009.000webhostapp.com//dregister.php";
        if (x.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (x.equals("Register")) {

            try {

                String username = params[1];
                String city = params[2];
                String email = params[3];
                String password = params[4];
                String confirmpass=params[5];
                String phonenumber = params[6];
                URL url = new URL(Register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        +URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8") + "&"
                        +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+"&"
                        + URLEncoder.encode("phoneno", "UTF-8") + "=" + URLEncoder.encode(phonenumber, "UTF-8")+"&"
                        + URLEncoder.encode("confirm password", "UTF-8") + "=" + URLEncoder.encode(confirmpass, "UTF-8");                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("BackgroundWorkerForDoc","Heyyy i m here"+result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("login success"))
        {
            Log.d("BackgroundWorker","I am here"+result);
            Intent intent=new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }
        else if(result.equals("insert success"))
        {
            Toast.makeText(context,"Registration Successs"+result,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,LogIn.class);
            context.startActivity(intent);

        }
        else if (result != "login success")
        {
            Toast.makeText(context,"User Not Registered",Toast.LENGTH_SHORT).show();
        }
        //alertDialog.setMessage(result);
        //alertDialog.show();

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
