package com.example.vilma.biometricrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckoutActivity extends BaseActivity implements TakePicFragment.PictureTakerListener {

    Spinner spinnerPickupLocation;
    String pickupLocation;
    Spinner spinnerDropoffLocation;
    String dropoffLocation;
    Spinner spinnerCar;
    String car;
    Spinner pickupDay;
    String pickDay;
    Spinner pickupMonth;
    String pickMonth;
    Spinner pickupYear;
    String pickYear;
    String pickDate;
    Spinner dropoffDay;
    String dropDay;
    Spinner dropoffMonth;
    String dropMonth;
    Spinner dropoffYear;
    String dropYear;
    String dropDate;

    ArrayAdapter<CharSequence> adapterLocation;
    ArrayAdapter<CharSequence> adapterCar;
    ArrayAdapter<CharSequence> adapterDay;
    ArrayAdapter<CharSequence> adapterMonth;
    ArrayAdapter<CharSequence> adapterYear;

    private String fragPhotoFilePath = null;
    EditText txtUsernameBox;
    String txtUsername;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    Button btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_checkout, contentFrameLayout);
        //setContentView(R.layout.activity_checkout);

        initUI();
    }


    private void initUI() {

//        btnHome = findViewById(R.id.btnHome);
        //Used for location spinners
        adapterLocation = ArrayAdapter.createFromResource(this, R.array.city_arrays, android.R.layout.simple_spinner_item);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Get pickup location
        spinnerPickupLocation = (Spinner) findViewById(R.id.spinnerPickupLocation);
        spinnerPickupLocation.setAdapter(adapterLocation);
        spinnerPickupLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickupLocation = parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),pickupLocation+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Get dropoff Location
        spinnerDropoffLocation = (Spinner) findViewById(R.id.spinnerDropoffLocation);
        spinnerDropoffLocation.setAdapter(adapterLocation);
        spinnerDropoffLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropoffLocation = parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),dropoffLocation+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Get car type
        adapterCar = ArrayAdapter.createFromResource(this, R.array.car_arrays, android.R.layout.simple_spinner_item);
        adapterCar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCar = (Spinner) findViewById(R.id.spinnerCarType);
        spinnerCar.setAdapter(adapterCar);
        spinnerCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car = parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),dropoffLocation+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Get date
        adapterDay = ArrayAdapter.createFromResource(this, R.array.day_arrays, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMonth = ArrayAdapter.createFromResource(this, R.array.month_arrays, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterYear = ArrayAdapter.createFromResource(this, R.array.year_arrays, android.R.layout.simple_spinner_item);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pickupDay = (Spinner) findViewById(R.id.pickDay);
        pickupDay.setAdapter(adapterDay);
        pickupDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickDay = pickupDay.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        pickupMonth = (Spinner) findViewById(R.id.pickMonth);
        pickupMonth.setAdapter(adapterMonth);
        pickupMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickMonth = pickupMonth.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        pickupYear = (Spinner) findViewById(R.id.pickYear);
        pickupYear.setAdapter(adapterYear);
        pickupYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickYear = pickupYear.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dropoffDay = (Spinner) findViewById(R.id.dropDay);
        dropoffDay.setAdapter(adapterDay);
        dropoffDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropDay = dropoffDay.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        dropoffMonth = (Spinner) findViewById(R.id.dropMonth);
        dropoffMonth.setAdapter(adapterMonth);
        dropoffMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropMonth = dropoffMonth.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        dropoffYear = (Spinner) findViewById(R.id.dropYear);
        dropoffYear.setAdapter(adapterYear);
        dropoffYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropYear = dropoffYear.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                storeRentalData();
//                Intent intentAccount = new Intent(CheckoutActivity.this,HomeActivity.class);
//                startActivity(intentAccount);
//            }
//        });

    }

    //method below allows TakePicFragment to grab the username the user typed in
    @Override
    public String getTxt(){
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        txtUsername = prefs.getString("Username", null);
        return txtUsername;
    }
    //grabs the photopath from the TakePicFragment and sets pic
    @Override
    public void picClick(String fragPhotoFilePath, String txtUsername) {
        this.txtUsername = txtUsername;
        this.fragPhotoFilePath = fragPhotoFilePath;
        //Toast.makeText(this, "we did it", Toast.LENGTH_SHORT).show();
    }

    //I think this runs after the the picture is taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Nice Pic!", Toast.LENGTH_LONG).show();
            Intent intentAccount = new Intent(CheckoutActivity.this,ThisYouActivity.class);
            startActivity(intentAccount);
            storeRentalData();
            uploadImage();
        }
    }

    private void uploadImage(){
        File file = new File(fragPhotoFilePath);
            String target = txtUsername + "_" + file.getName();
            String source = txtUsername + "_prime.jpg";

          S3Upload upload = new S3Upload(this, fragPhotoFilePath, target);
            upload.execute();

        try {
            upload.get();
        } catch (InterruptedException e) {
            Toast.makeText(this, "IM SORRY vilma", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(this, "IM SORRY gabe", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //Stores first, last, and user name in the shared preferences
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhotoPath", fragPhotoFilePath);
        editor.putString("Target",target);
        editor.putString("Source",source);
        editor.commit();

    }

    private void storeRentalData(){

        pickDate = pickDay+"/"+pickMonth+"/"+pickYear;
        dropDate = dropDay+"/"+dropMonth+"/"+dropYear;

        //Use sharedPreferences to pass data to another activities without starting the other activity
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Pickup location", pickupLocation);
        editor.putString("Dropoff location", dropoffLocation);
        editor.putString("Pickup date", pickDate);
        editor.putString("Dropoff date", dropDate);
        editor.putString("Car", car);
        editor.commit();

        Toast.makeText(getApplicationContext(), "Reservation info was stored", Toast.LENGTH_LONG).show();
    }

}
