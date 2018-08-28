package com.example.nakuld.medhelp99;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.HashMap;

public class searchDoctor extends AppCompatActivity {
    EditText cityName;
    Button searchButtonForDocto;
    String cityname;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public String JsonString;
    public static ListView doctorNameListView;
    public static ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        cityName = (EditText) findViewById(R.id.city_name);
        contactList = new ArrayList<>();
        doctorNameListView = (ListView) findViewById(R.id.list2);
        searchButtonForDocto= (Button) findViewById(R.id.search_button_doctor);
        searchButtonForDocto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMethod();
            }
        });
    }
    private void searchMethod() {
        cityname = cityName.getText().toString();
        BackgroundTask12 backgroundTask1 = new BackgroundTask12();
        contactList.clear();
        backgroundTask1.execute(cityname);
    }
    public class BackgroundTask12 extends AsyncTask<String, Void, String> {
        String json_url;

        @Override
        protected String doInBackground(String... params) {
            String name;
            name = params[0];
            json_url = "http://medhelp009.000webhostapp.com/json_search_doctor.php";
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
            return null;
        }

        @Override
        protected void onPreExecute() {
            json_url = "http://medhelp009.000webhostapp.com/json_get_data.php";
        }

        @Override
        protected void onPostExecute(String result) {
         //   RecommendationMedicine recommendationMedicine = new RecommendationMedicine();
            ArrayList doctorName = new ArrayList();
            ArrayList doctorPhone = new ArrayList();
            ArrayList doctorCity = new ArrayList();
            String phoneno = " ", city = " ",nameofDoctor= " ";
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    phoneno = jo.getString("Phone No.");
                    city = jo.getString("city");
                    nameofDoctor = jo.getString("name");
                    HashMap<String, String> contact = new HashMap<>();
                    contact.put("name", nameofDoctor);
                    contact.put("city", city);
                    contact.put("phone No",phoneno);
                    Log.d("searchDoctor","Hiii i m hereeel"+contact);
                    contactList.add(contact);
                    count++;
//                    doctorName.add(nameofDoctor);
//                    doctorPhone.add(phoneno);
//                    doctorCity.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListAdapter adapter = new SimpleAdapter(
                    searchDoctor.this, contactList,
                    R.layout.list_item_for_doctor, new String[]{"name", "city",
                    "phone No"}, new int[]{R.id.name,
                    R.id.city, R.id.phoneNo});
            doctorNameListView.setAdapter(adapter);
        }
    }
}
