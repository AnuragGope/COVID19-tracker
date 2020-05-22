package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    public EditText search;
    public SimpleArcLoader arc;
    public ListView lv;

    public static List<Country> countryList=new ArrayList<>();
    Country  country;
    MyCustomAdapter myCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        search=findViewById(R.id.editText);
        arc=findViewById(R.id.arc);
        lv=findViewById(R.id.listView);

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Main2Activity.this,detail.class).putExtra("position",position));
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 myCustomAdapter.getFilter().filter(s);
                 myCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/countries";
        arc.start();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try{
                        JSONArray jsonArray=new JSONArray(response.toString());
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String countryName=jsonObject.getString("country");
                            String cases=jsonObject.getString("cases");
                            String todayCases=jsonObject.getString("todayCases");
                            String deaths=jsonObject.getString("deaths");
                            String todayDeaths=jsonObject.getString("todayDeaths");
                            String recovered=jsonObject.getString("recovered");
                            String active=jsonObject.getString("active");
                            String critical=jsonObject.getString("critical");

                            JSONObject object=jsonObject.getJSONObject("countryInfo");
                            String flagUrl=object.getString("flag");

                            country=new Country(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);
                            countryList.add(country);
                        }
                        myCustomAdapter = new MyCustomAdapter(Main2Activity.this,countryList);
                        lv.setAdapter(myCustomAdapter);
                        arc.stop();
                        arc.setVisibility(View.GONE);

                    }catch(JSONException e){
                        e.printStackTrace();
                        arc.stop();
                        arc.setVisibility(View.GONE);
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main2Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                arc.stop();
                arc.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
}}
