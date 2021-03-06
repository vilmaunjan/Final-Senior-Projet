package com.example.vilma.biometricrecognition;

/**
 * Created by Vilma on 1/21/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import android.content.Context;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;
//import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.InvalidParameterException;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.model.S3DataSource;

import java.util.List;

public class ComparePictures extends AsyncTask<Object, String, Float> {

    private static final String LOG_TAG = ComparePictures.class.getSimpleName();
    private Context mContext;
    private String mSource;
    private String mTarget;
    private Float confidence = 0F;
    private static AmazonRekognitionClient rekognitionClient;
    private static Float similarityThreshold = 1F;
    private static String bucket = Constants.BUCKET_NAME;

    ThisYouActivity mActivity;
    AccountActivity aActivity;


    //Constructor for ThiYouActivity
    public ComparePictures(Context context, String source, String target, ThisYouActivity activity){
        mContext = context;

        mSource = source;
        mTarget = target;
        this.mActivity = activity;
    }

    //Constructor for ThiYouActivity
    public ComparePictures(Context context, String source, String target, AccountActivity activity){
        mContext = context;

        mSource = source;
        mTarget = target;
        aActivity = activity;
    }


    @Override
    protected Float doInBackground(Object... params) {
        //String source = (String)params[0];
        //String target = (String)params[1];

        rekognitionClient = Util.getRekognitionClient(mContext);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(mSource).withBucket(bucket)))
                .withTargetImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(mTarget).withBucket(bucket)))
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        try{
            CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);

            // Display results
            List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            for (CompareFacesMatch match: faceDetails){
                ComparedFace face= match.getFace();
                BoundingBox position = face.getBoundingBox();
                confidence = match.getSimilarity();
                System.out.println("Face at " + position.getLeft().toString()
                        + " " + position.getTop()
                        + " matches with " + face.getConfidence().toString()
                        + "% confidence.");
            }
        } catch (InvalidParameterException e){
            Log.d(LOG_TAG, "TEST CASE 4: There is not a face in the image.");
        }
        return confidence;
    }

    protected void onPostExecute(Float result) {
        mActivity.compareFinish(result);
    }
}