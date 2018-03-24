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
    //private TextView lblSimilarity;
    private ImageView primePic;
    String previewPhotoPath;
    //Float mResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_primepicture, container, false);
/*
        lblSimilarity = (TextView) rootView.findViewById(R.id.lblPrimeSimilarity);
        primePic = (ImageView) rootView.findViewById(R.id.primePicture);

        SharedPreferences prefs = rootView.getContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        mResult = prefs.getFloat("Similarity",-1);
        previewPhotoPath = prefs.getString("PhotoPath",null);

        lblSimilarity.setText("Similarity of: "+mResult+"% ");*/
        return rootView;
    }

    private void downloadPicture(){

    }


}