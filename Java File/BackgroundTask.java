package com.example.nakuld.medhelp99;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by nakulD on 3/22/2018.
 */

class BackgroundTask extends AsyncTask<String,Void,String> {
    String json_url,json_symptums;
    JSONArray jsonArray;
    JSONObject jsonObject;
    String JsonString;
    String Activity;
    public Context context;
    public  ListView medRecoName;
    public  ArrayAdapter listAdapter ;

    public BackgroundTask(Context context11,ListView listView)
    {
        medRecoName = listView;
        context = context11;
    }
    public BackgroundTask(){

    }
    @Override
    protected void onPreExecute() {
        json_url = "http://medhelp009.000webhostapp.com/json_get_data.php";
        json_symptums = "http://medhelp009.000webhostapp.com/json_symptums.php";
    }
    @Override
    protected String doInBackground(String... params) {
        String name;
        name = params[0];
        Activity = params[1];
        if (Activity.equals("MainActivity method")) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                StringBuilder sb = new StringBuilder();
                while ((JsonString = bufferedReader.readLine()) != null) {
                    sb.append(JsonString + "\n");
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (params[1].equals("Substitute Activity")){
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                StringBuilder sb=new StringBuilder();
                while((JsonString = bufferedReader.readLine())!=null){
                    sb.append(JsonString+"\n");
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(params[1].equals("Recommendation Activity")){
            try {
                URL url = new URL(json_symptums);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                StringBuilder sb=new StringBuilder();
                while((JsonString = bufferedReader.readLine())!=null){
                    sb.append(JsonString+"\n");
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
        @Override
        protected void onPostExecute (String result){
        if(Activity.equals("MainActivity method")){
            String cname = " ", price = " ", dosageform = " ", conscituent = " ", conscituentperunit = " ", sideeffects = " ", warning = " ", isgeneric = " ";
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    cname = jo.getString("cname");
                    price = jo.getString("price");
                    dosageform = jo.getString("dosageform");
                    conscituent = jo.getString("conscituent");
                    conscituentperunit = jo.getString("conscituentperunit");
                    sideeffects = jo.getString("sideeffects");
                    warning = jo.getString("warning");
                    isgeneric = jo.getString("isgeneric");
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity mainActivity = new MainActivity();

            mainActivity.setcNameTextView(cname);
            mainActivity.setPriceTextview(price);
            mainActivity.setConstituentTextview(conscituent);
            mainActivity.setConstituentPerUnitTextview(conscituentperunit);
            mainActivity.setDosageFormTextview(dosageform);
            mainActivity.setSideEffectsTextview(sideeffects);
            mainActivity.setWarningTextview(warning);
        }
        else if (Activity.equals("Substitute Activity")){
            String medicinename= " ",price = " ";
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count <jsonArray.length()){
                    JSONObject jo=jsonArray.getJSONObject(count);
                    medicinename=jo.getString("substitute");
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MedicineSubstitute medicineSubstitute = new MedicineSubstitute();
            medicineSubstitute.setMnameTextview(medicinename);
        }
        else if (Activity.equals("Recommendation Activity")) {
            RecommendationMedicine recommendationMedicine = new RecommendationMedicine();
            ArrayList medicineName = new ArrayList();
            String medicinename = " ", conscituentperunit = " ";
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    medicinename = jo.getString("name");
                    conscituentperunit = jo.getString("conscituentperunit");
                    count++;
                    medicineName.add(medicinename);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listAdapter = new ArrayAdapter(context, R.layout.simplerow, medicineName);

            if (medRecoName == null) {
                Log.d("RecommendationMedicine", "I am here at Reco" + medicineName);
            } else {
                medRecoName.setAdapter(listAdapter);
            }
        }
    }
}