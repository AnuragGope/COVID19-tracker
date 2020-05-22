package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class detail extends AppCompatActivity {

    public TextView tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private int positionCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);
        tv8=findViewById(R.id.tv8);
        tv9=findViewById(R.id.tv9);


        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+Main2Activity.countryList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv2.setText(Main2Activity.countryList.get(positionCountry).getCountry());
        tv3.setText(Main2Activity.countryList.get(positionCountry).getCases());
        tv4.setText(Main2Activity.countryList.get(positionCountry).getTodayCases());
        tv5.setText(Main2Activity.countryList.get(positionCountry).getDeaths());
        tv6.setText(Main2Activity.countryList.get(positionCountry).getTodayDeaths());
        tv7.setText(Main2Activity.countryList.get(positionCountry).getRecovered());
        tv8.setText(Main2Activity.countryList.get(positionCountry).getActive());
        tv9.setText(Main2Activity.countryList.get(positionCountry).getCritical());


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
