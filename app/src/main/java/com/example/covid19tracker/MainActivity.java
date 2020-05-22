package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public TextView tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
    public SimpleArcLoader sal;
    ScrollView sv;
    PieChart pc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6);
        tv7=(TextView)findViewById(R.id.tv7);
        tv8=(TextView)findViewById(R.id.tv8);
        tv9=(TextView)findViewById(R.id.tv9);
        tv10=findViewById(R.id.tv10);

        sal=findViewById(R.id.loader);
        sv=findViewById(R.id.scrollView);
        pc=findViewById(R.id.pie);

        fetchData();

    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/all";
        sal.start();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response.toString());

                    tv2.setText(jsonObject.getString("cases"));
                    tv3.setText(jsonObject.getString("recovered"));
                    tv4.setText(jsonObject.getString("critical"));
                    tv5.setText(jsonObject.getString("active"));
                    tv6.setText(jsonObject.getString("todayCases"));
                    tv7.setText(jsonObject.getString("deaths"));
                    tv8.setText(jsonObject.getString("todayDeaths"));
                    tv9.setText(jsonObject.getString("testsPerOneMillion"));
                    tv10.setText(jsonObject.getString("affectedCountries"));

                    pc.addPieSlice(new PieModel("cases",Integer.parseInt(tv2.getText().toString()),Color.parseColor("#F7C83F")));
                    pc.addPieSlice(new PieModel("recovered",Integer.parseInt(tv3.getText().toString()),Color.parseColor("#8BC34A")));
                    pc.addPieSlice(new PieModel("deaths",Integer.parseInt(tv7.getText().toString()),Color.parseColor("#FF5722")));
                    pc.addPieSlice(new PieModel("active",Integer.parseInt(tv5.getText().toString()),Color.parseColor("#3F51B5")));

                    sal.stop();
                    sal.setVisibility(View.GONE);
                    sv.setVisibility(View.VISIBLE);

                }catch (JSONException e){
                    e.printStackTrace();
                    sal.stop();
                    sal.setVisibility(View.GONE);
                    sv.setVisibility(View.VISIBLE);
                };

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                sal.stop();
                sal.setVisibility(View.GONE);
                sv.setVisibility(View.VISIBLE);
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view){

        Intent intent=new Intent( this,Main2Activity.class);
        startActivity(intent);

    }
}
