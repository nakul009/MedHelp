package com.example.nakuld.medhelp99;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    String[]medicine_names;
    Button GJ;
    DrawerLayout mDrawerLayout;
    NavigationView navigation;
    private ActionBarDrawerToggle mToggle;
    public static TextView cNameTextView;
    public static TextView warningTextView;
    public static TextView priceTextView;
    public static TextView dosageformTextView,conscituentTextView,conscituentPerUnitTextView,sideeffectsTextView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GJ = (Button) findViewById(R.id.search_button);
        cNameTextView = (TextView) findViewById(R.id.cname11);
        if (cNameTextView == null) {
            Log.d("MainActivity", "I am here onCreate");
        } else {
            Log.d("MainActivity", "I am here cNameTextView not null");
        }
        priceTextView = (TextView) findViewById(R.id.price);
        dosageformTextView = (TextView) findViewById(R.id.dosageform);
        conscituentTextView = (TextView) findViewById(R.id.conscituent);
        conscituentPerUnitTextView = (TextView) findViewById(R.id.conscituentperunit);
        sideeffectsTextView = (TextView) findViewById(R.id.sideeffects);
        warningTextView = (TextView) findViewById(R.id.warning);
        //Name = (EditText) findViewById(R.id.search_text);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.medicines);
        medicine_names = getResources().getStringArray(R.array.medicine_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, medicine_names);
        autoCompleteTextView.setAdapter(adapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout1);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            String j =(String) b.get("name");
            autoCompleteTextView.setText(j);
        }
        navigation = (NavigationView) findViewById(R.id.navigationview);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.Substitute:
                        Intent inent = new Intent(MainActivity.this, MedicineSubstitute.class);
                        startActivity(inent);
                        break;
                    case R.id.Recommendation:
                        Intent inent1 = new Intent(MainActivity.this, RecommendationMedicine.class);
                        startActivity(inent1);
                        Log.d("MainActivity","I am here");
                        break;
                    case R.id.alarm:
                        Intent intent2=new Intent(MainActivity.this,Alram.class);
                        startActivity(intent2);
                        break;
                    case R.id.news:
                        Intent inent2 = new Intent(MainActivity.this, News.class);
                        startActivity(inent2);
                        break;
                }
                return false;
            }
        });

        GJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveInfo() {
        name = autoCompleteTextView.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(name,"MainActivity method");
    }
    public void setPriceTextview(String price)
    {
        priceTextView.setText(price);
    }
    public void setDosageFormTextview(String dosageform)
    {
        dosageformTextView.setText(dosageform);
    }
    public void setConstituentTextview(String consci)
    {
        conscituentTextView.setText(consci);
    }
    public void setConstituentPerUnitTextview(String consciperunit)
    {
        conscituentPerUnitTextView.setText(consciperunit);
    }
    public void setSideEffectsTextview(String side)
    {
        sideeffectsTextView.setText(side);
    }

    public void setWarningTextview(String warning)
    {
        warningTextView.setText(warning);
    }
    public void setcNameTextView(String cName)
    {
            cNameTextView.setText(cName);
    }
}
