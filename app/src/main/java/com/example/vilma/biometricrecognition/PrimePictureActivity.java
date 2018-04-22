package com.example.vilma.biometricrecognition;

/**
 * Created by Vilma on 3/18/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class PrimePictureActivity extends Fragment{

    private static final String TAG = AccountActivity.class.getSimpleName();
    private ImageView primePic;
    String primePhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_primepicture, container, false);

        primePic = (ImageView) rootView.findViewById(R.id.primePicture);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);
        primePhotoPath = prefs.getString("PrimePath",null);
        setPic();

        return rootView;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = 210;
        int targetH = 320;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(primePhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(primePhotoPath, bmOptions);
        primePic.setImageBitmap(bitmap);
    }


}