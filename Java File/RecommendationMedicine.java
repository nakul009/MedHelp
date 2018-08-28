package com.example.nakuld.medhelp99;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class RecommendationMedicine extends AppCompatActivity {
    EditText medicineNameforReco;
    public static ListView medRecoName;
    Button searchButtonForReco;
    public static ArrayAdapter listAdapter;
    String name;
    Button msearchDoctorButton;
    String JsonString;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public static ArrayList<String> al = new ArrayList<>();
    ArrayList medicineName = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_medicine);
        msearchDoctorButton = (Button) findViewById(R.id.buttonforsearchdoctor);
        Log.d("MainActivity", "I am here");
        medicineNameforReco = (EditText) findViewById(R.id.symptomsofMedicine);
        medRecoName = (ListView) findViewById(R.id.list);
        searchButtonForReco = (Button) findViewById(R.id.search_button3);
        searchButtonForReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sympromsmethod();
            }
        });
        medRecoName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("RecommendationMedicine", "Hiiioooooo" + medicineName);
                String x = (String) medicineName.get(position);
                Intent intent = new Intent(RecommendationMedicine.this, MainActivity.class);
                intent.putExtra("name", x);
                startActivity(intent);
                Log.d("RecommendationMedicine", "Hiii nakul is here" + x);
            }
        });
        msearchDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendationMedicine.this, searchDoctor.class);
                startActivity(intent);
            }
        });
    }

    private void sympromsmethod() {
        name = medicineNameforReco.getText().toString();
 //       BackgroundTask backgroundTask1 = new BackgroundTask(RecommendationMedicine.this, medRecoName);
  //      backgroundTask1.execute(name, "Recommendation Activity");

        BackgroundTask14 backgroundTask1 = new BackgroundTask14();
        al.clear();
        backgroundTask1.execute(name);

    }

    class BackgroundTask14 extends AsyncTask<String, Void, String> {
        String json_symptums = "http://medhelp009.000webhostapp.com/json_symptums.php";
        @Override
        protected String doInBackground(String... params) {
            String name;
            name = params[0];
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
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
           // RecommendationMedicine recommendationMedicine = new RecommendationMedicine();
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
            listAdapter = new ArrayAdapter(getApplicationContext(), R.layout.simplerow, medicineName);

            if (medRecoName == null) {
                Log.d("RecommendationMedicine", "I am here at Reco" + medicineName);
            } else {
                medRecoName.setAdapter(listAdapter);
            }
        }
    }
}
