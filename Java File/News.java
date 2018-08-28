package com.example.nakuld.medhelp99;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class News extends AppCompatActivity {
    ArrayList<String> links;

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    String url009;
    // URL to get contacts JSON
    private static String url = "http://medhelp009.000webhostapp.com/News.JSON";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        contactList = new ArrayList<>();
        links = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.list1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Uri uri=Uri.parse(links.get(position));
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
            }
        });

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(News.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String category = c.getString("category");
                        String pubDate = c.getString("pubDate");
                        String language = c.getString("language");
                        url009 = c.getString("url009");
                        links.add(url009);

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("category", category);
                        contact.put("pubDate", pubDate);
                        contact.put("language", language);
                        contact.put("url",url009);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    News.this, contactList,
                    R.layout.list_item, new String[]{"name", "category",
                    "language","pubDate"}, new int[]{R.id.name,
                    R.id.category, R.id.language,R.id.pubDate});

            lv.setAdapter(adapter);
        }
    }
}
//    ListView lvRss;
//    ArrayList<String> title;
//    ArrayList<String> links;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
//        lvRss = (ListView) findViewById(R.id.lvRss);
//        title = new ArrayList<String>();
//        links = new ArrayList<String>();
//        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Uri uri =Uri.parse(links.get(position));
//                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
//            }
//        });
//        new ProcessInBackground().execute();
//    }
//
//    public InputStream getInputStream(URL url){
//        try{
//            return url.openConnection().getInputStream();
//        }
//        catch (Exception e){
//            return null;
//        }
//    }
//    public class ProcessInBackground extends AsyncTask<String,Void,Exception> {
//        ProgressDialog progressDialog = new ProgressDialog(News.this);
//        Exception exception = null;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog.setMessage("Busy loading rss feed .... Please Wait ....");
//            progressDialog.show();
//        }
//
//        @Override
//        protected Exception doInBackground(String... params) {
//            try{
//                URL url=new URL("http://feeds.news24.com/articles/fin24/tech/rss");
//                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                factory.setNamespaceAware(false);
//                XmlPullParser xpp = factory.newPullParser();
//                xpp.setInput(getInputStream(url),"UTF_8");
//                boolean insideItem = false;
//                int eventType = xpp.getEventType();
//                while (eventType != XmlPullParser.END_DOCUMENT){
//                    if(eventType == XmlPullParser.START_TAG){
//                        if(xpp.getName().equalsIgnoreCase("item")){
//                            insideItem = true;
//                        }
//                        else if(xpp.getName().equalsIgnoreCase("title")){
//                            if(insideItem){
//                                title.add(xpp.nextText());
//                            }
//                        }
//                        else if(xpp.getName().equalsIgnoreCase("link")){
//                            if(insideItem){
//                                links.add(xpp.nextText());
//                            }
//                        }
//                    }
//                    else if(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
//                        insideItem = false;
//                    }
//                    eventType = xpp.next();
//                }
//            }
//            catch(MalformedURLException e){
//                exception = e;
//            }
//            catch (XmlPullParserException e){
//                exception = e;
//            }
//            catch (IOException e){
//                exception = e;
//            }
//            return exception;
//        }
//
//        @Override
//        protected void onPostExecute(Exception s) {
//            super.onPostExecute(s);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(News.this,android.R.layout.simple_list_item_1,title);
//            lvRss.setAdapter(adapter);
//            progressDialog.dismiss();
//        }
//    }
