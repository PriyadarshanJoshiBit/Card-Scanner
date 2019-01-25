package io.card.payment.firebase;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import io.card.payment.firebase.model.Card;

public class FirebaseReader {

    private static final String TAG ="FirebaseReader";
    FirebaseVisionText visionText = null;

    protected FirebaseVisionText readPrintedCard(Bitmap cardImage){


        if(cardImage == null){
            Log.i("FirebaseReader", "Bitmap Found null");
            return null;
        }

        //FirebaseApp.initializeApp(this.scanActivity);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(cardImage);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {
                                 visionText=texts;
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

        return visionText;
    }


}
