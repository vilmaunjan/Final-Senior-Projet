package com.example.vilma.biometricrecognition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jclew on 1/31/2018.
 */

public class ThisYouActivity extends BaseActivity{
    ImageView preview;
    Button yes;
    Button no;
    String txtFirstname;
    String txtLastname;
    String txtUsername;
    String previewPhotoPath;
    String target;
    String source;
    Float result;

    @Override  //calls this method immediately when this activity is called
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thisyou);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //so it doesnt show any text in the toolbar

        initUI();
    }

    private void initUI() {
        //preview = (ImageView)findViewById(R.id.previewImageView);
        yes = findViewById(R.id.btnYes);
        no = findViewById(R.id.btnNo);

/*
        Intent intentExtras = getIntent();
        Bundle extraBundle = intentExtras.getExtras();
        txtFirstname = extraBundle.getString("FirstName");
        txtLastname = extraBundle.getString("LastName");
        txtUsername = extraBundle.getString("Username");
        previewPhotoPath = extraBundle.getString("PhotoPath");
        target = extraBundle.getString("Target");
        source = extraBundle.getString("Source");
        username = extraBundle.getString("Username");
        */
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        txtFirstname = prefs.getString("FirstName",null);
        txtLastname = prefs.getString("LastName",null);
        txtUsername = prefs.getString("Username",null);
        previewPhotoPath = prefs.getString("PhotoPath",null);
        target = prefs.getString("Target",null);
        source = prefs.getString("Source",null);

//        setPic();

        yes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //this method does like pretty much all the work....like seriously-_-
                comparePic();
            }
        });

        no.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //this method does like pretty much all the work....like seriously-_-
                Intent home = new Intent(ThisYouActivity.this, HomeActivity.class);
                startActivity(home);
            }
        });


    }

    private void comparePic() {
        ComparePictures j = new ComparePictures(this, source, target, this);
        j.execute();
    }

    void compareFinish(Float result){
        this.result = result;
        if(result != null) {
            if (result > 80) {
                Toast.makeText(this, "Thank you "+txtFirstname+"! You have successfully checked out"
                        , Toast.LENGTH_LONG).show();

                Intent registerIntent = new Intent(ThisYouActivity.this, HomeActivity.class);
       /*         Bundle bundle = new Bundle();
                bundle.putString("FirstName", txtFirstname);
                bundle.putString("LastName", txtLastname);
                bundle.putString("Username", txtUsername);
                registerIntent.putExtras(bundle);
         */     startActivity(registerIntent);
                finish();

            } else {
                Toast.makeText(this, "You are not "+txtFirstname+" "+txtLastname+". Match percentage "
                        + this.result.toString(), Toast.LENGTH_LONG).show();
                Intent failedIntent = new Intent(ThisYouActivity.this, PreviewActivity.class);
                startActivity(failedIntent);
            }
        }else{
            Toast.makeText(this, "Picture doesn't match Prime", Toast.LENGTH_LONG).show();
            Intent failedIntent = new Intent(ThisYouActivity.this, PreviewActivity.class);
            startActivity(failedIntent);
        }

        //It is outside of the conditional statements so that it doesnt crush when there is no pic,
        //but there is a BUG, when in terms & cond, user clicks no, it doesnt update the similarity score
        SharedPreferences prefs = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("Similarity", result);
        editor.commit();
    }

/*
    //DONT NEED THE FOLLOWING 2 METHODS BECAUSE WE ARE NO DISPLAYING A PICTURE
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = 350;
        int targetH = 200;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(previewPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(previewPhotoPath, bmOptions);
        bitmap = RotateBitmap(bitmap,270);
        preview.setImageBitmap(bitmap);
    }
*/
}
