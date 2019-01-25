package io.card.payment;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class BitmapConsumer implements Runnable {
    boolean cardDetected;
    BlockingQueue<Bitmap> bitmapQueue;

    BitmapConsumer(BlockingQueue<Bitmap> bitmapQueue){
        this.bitmapQueue = bitmapQueue;
    }
    public void run(){
        while(true) {
            try {
                Bitmap bitmap = bitmapQueue.take();
                runTextRecognition(bitmap);
                if(cardDetected){

                }
            } catch (InterruptedException ie) {

            }
        }
    }


    private void runTextRecognition(Bitmap bitmap) {

        if(bitmap == null){
            Log.i(TAG, "Bitmap Found null");
            return;
        }
        Log.i(TAG, "Bitmap Found not null");
        //FirebaseApp.initializeApp(this.scanActivity);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        recognizer.processImage(image)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText texts) {

                                processTextRecognitionResult(texts);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        String detectedText="";

        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            return;
        }
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        Log.i("Firebase-Scanner","Text-Found "+texts.getText() + ": " + date);
        for (int i = 0; i < blocks.size(); i++) {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();

            for (int j = 0; j < lines.size(); j++) {


                detectedText = lines.get(j).getText().replaceAll("\\s","");
                Pattern cardNumberPattern = Pattern.compile("[0-9]{16}");
                if(cardNumberPattern.matcher(detectedText).matches()){
                    Log.i("Firebase-Scanner","Card-Found "+texts.getText() + ": " + date);
                    cardDetected = true;
                }

            }
        }
    }
}