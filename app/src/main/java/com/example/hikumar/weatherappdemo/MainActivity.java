package com.example.hikumar.weatherappdemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements GetRawData.AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fetchButton = (Button) findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                EditText cityText = (EditText) findViewById(R.id.cityText);
                String city = cityText.getText().toString();
                String api = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=25039d7a8d6f6bb0c0f06edea90d50b1";
                new GetRawData(MainActivity.this).execute(api);
            }
        });

    }

    @Override
    public void processFinish(String output) {
        TextView resultText = (TextView) findViewById(R.id.resultText);

        try {
            JSONObject data = new JSONObject(output);
            double temp = data.getJSONObject("main").getDouble("temp") - 274.15;
            DecimalFormat two = new DecimalFormat("#0.00");
            String temperature = two.format(temp);
            String place = data.getString("name");
            resultText.setText("Temperature of " + place + " is: " + temperature + "°C");
            resultText.setMovementMethod(new ScrollingMovementMethod());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
