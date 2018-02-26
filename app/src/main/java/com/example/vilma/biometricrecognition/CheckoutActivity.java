package com.example.vilma.biometricrecognition;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends BaseActivity {

    EditText txtCity;
    String city;
    String car;
    String location;
    Spinner spinnerLocation;
    Spinner spinnerCar;

    ArrayAdapter<CharSequence> adapterLocation;
    ArrayAdapter<CharSequence> adapterCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_checkout, contentFrameLayout);
        //setContentView(R.layout.activity_checkout);


        initUI();
    }


    private void initUI() {

        txtCity = (EditText) findViewById(R.id.txtCity);
/*
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        adapterLocation = ArrayAdapter.createFromResource(this, R.array.city_arrays, android.R.layout.simple_spinner_item);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapterLocation);
        spinnerLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
                location = parent.getSelectedItem().toString();
            }
        });


        spinnerCar = (Spinner) findViewById(R.id.spinnerCarType);
        adapterCar = ArrayAdapter.createFromResource(this, R.array.car_arrays, android.R.layout.simple_spinner_item);
        adapterCar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCar.setAdapter(adapterCar);
        spinnerCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
                car = parent.getSelectedItem().toString();
            }
        });
*/
    }


    private void storeData(){

        city = txtCity.getText().toString();

        //Use sharedPreferences to pass data to another activities without starting the other activity
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("City", city);
        editor.putString("Location", location);
        editor.commit();
    }
}
