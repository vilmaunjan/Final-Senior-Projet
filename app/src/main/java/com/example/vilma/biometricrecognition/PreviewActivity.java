package com.example.vilma.biometricrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 3/27/18.
 */

public class PreviewActivity extends BaseActivity implements TakePicFragment.PictureTakerListener {
        TextView pickUp_Location;
        TextView dropOff_Location;
        TextView pickUp_Date;
        TextView dropOff_Date;
        TextView carType;
        Button btnReservation;

        private String fragPhotoFilePath = null;
        String txtUsername;
        static final int REQUEST_IMAGE_CAPTURE = 1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
            getLayoutInflater().inflate(R.layout.activity_preview, contentFrameLayout);
            //setContentView(R.layout.activity_checkout);

            initUI();
        }


    private void initUI() {

        pickUp_Location = findViewById(R.id.textView_1);
        dropOff_Location = findViewById(R.id.textView_2);
        pickUp_Date = findViewById(R.id.textView_3);
        dropOff_Date = findViewById(R.id.textView_4);
        carType = findViewById(R.id.textView_5);

        btnReservation = findViewById(R.id.btnNo);

        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        pickUp_Location.setText("Pick-Up Location: "+prefs.getString("Pickup location",null));
        dropOff_Location.setText("Drop-Off Location: "+prefs.getString("Dropoff location",null));
        pickUp_Date.setText("Pick-Up Date: "+prefs.getString("Pickup date",null));
        dropOff_Date.setText("Drop-Off Location: "+prefs.getString("Dropoff date",null));
        carType.setText("Car Type: "+prefs.getString("Car",null));


        TakePicFragment fragment_obj = (TakePicFragment)getSupportFragmentManager().
                findFragmentById(R.id.fragment2);
        fragment_obj.updateTextView();

        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAccount = new Intent(PreviewActivity.this,CheckoutActivity.class);
                startActivity(intentAccount);
            }
        });
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
            Intent intentAccount = new Intent(PreviewActivity.this,ThisYouActivity.class);
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

        Toast.makeText(getApplicationContext(), "Reservation info was stored", Toast.LENGTH_LONG).show();
    }

}
