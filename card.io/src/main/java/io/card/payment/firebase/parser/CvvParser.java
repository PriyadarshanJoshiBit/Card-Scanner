package io.card.payment.firebase.parser;

import java.util.regex.Pattern;

import io.card.payment.firebase.model.DetectedCard;

public class CvvParser implements ParserChain {

    ParserChain nextChain;
    Pattern expiryDatePattern = Pattern.compile("[0-9]{3}");

    public CvvParser(){
        this.nextChain = null;
    }

    @Override
    public void parse(DetectedCard detectedCard, String detectedText) {

        if(expiryDatePattern.matcher(detectedText).matches()){
           // activity.cvv = detectedText;
        }
    }
}
