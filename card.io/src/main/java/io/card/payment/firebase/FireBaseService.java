package io.card.payment.firebase;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.List;

import io.card.payment.firebase.model.CardUC;
import io.card.payment.firebase.parser.DataExtractor;


public class FireBaseService {

    FirebaseReader reader;
    DataExtractor extractor;
    CardUC detectedCard;
    CardDetectionHandler cardDetectionHandler;
    public  FireBaseService(){
        reader = new FirebaseReader();
        extractor = new DataExtractor();
    }

    String readTextLine;
    public CardUC predictPrintedCard(CardDetectionHandler cardDetectionHandler){
        this.cardDetectionHandler = cardDetectionHandler;
        this.detectedCard =cardDetectionHandler.getCardUCInstance();
        if( detectedCard.getImg() == null )  throw new InvalidParameterException("Image Bitmap found null.");

        Bitmap imageData = detectedCard.getImg();
        FirebaseReader reader = new FirebaseReader();
        reader.readPrintedCard(imageData,this);


        return detectedCard;
    }
    public CardUC processRecognizedText(FirebaseVisionText recgnzdText){
        List<FirebaseVisionText.TextBlock> blocks = recgnzdText.getTextBlocks();


        for (FirebaseVisionText.TextBlock block : blocks) {

            List<FirebaseVisionText.Line> lines = block.getLines();

            for (FirebaseVisionText.Line line : lines) {
                readTextLine = line.getText().replaceAll("\\s", "");

                cardDetectionHandler.verifyAttempts();

                extractor.extractData(detectedCard, readTextLine);
            }
        }

        return detectedCard;

    }

}
