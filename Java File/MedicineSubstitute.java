package com.example.nakuld.medhelp99;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class MedicineSubstitute extends AppCompatActivity {
    AutoCompleteTextView medicineName;
    Button searchButton;
    String[]medicine_names;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public  static TextView medSubName;
    public String JsonString;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_substitute);
        medicineName = (AutoCompleteTextView) findViewById(R.id.medicinesSubstituteName);
        medicine_names = getResources().getStringArray(R.array.medicine_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, medicine_names);
        medicineName.setAdapter(adapter);
        medSubName = (TextView) findViewById(R.id.medicine_name2);
        searchButton = (Button) findViewById(R.id.search_button1);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sympromsmethod();
            }
        });
    }

    private void sympromsmethod() {
        name = medicineName.getText().toString();
        BackgroundTask backgroundTask1 = new BackgroundTask();
        backgroundTask1.execute(name, "Substitute Activity");
    }

    public void setMnameTextview(String medSubstituteName) {
        medSubName.setText(medSubstituteName);
    }

    class BackgroundTask1 extends AsyncTask<String, Void, String> {
        String json_url;

        @Override
        protected String doInBackground(String... params) {
            String name;
            name = params[0];
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
            String medicinename = " ", price = " ";
            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    medicinename = jo.getString("substitute");
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //          TextView cTname=(TextView) findViewById(R.id.medicine_name2);
            //          cTname.setText(medicinename);
        }

    }
}
