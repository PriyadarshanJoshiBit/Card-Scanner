package io.card.payment.firebase.parser;

import java.util.regex.Pattern;

import io.card.payment.firebase.model.DetectedCard;

public class ExpiryDateParser implements ParserChain {

    ParserChain nextChain;
    Pattern expiryDatePattern = Pattern.compile("[0-9]{2}/[0-9]{2}");

    public ExpiryDateParser(){
        this.nextChain = new ExpiryMulitpleDateParser();
    }



    @Override
    public void parse(DetectedCard detectedCard, String detectedText) {

        if(expiryDatePattern.matcher(detectedText).matches()){
           // activity.setExpiryDateDetected(true);
            setExpiryDate(detectedText);
        }else{
            this.nextChain.parse(detectedCard,detectedText);
        }
    }

    public void setExpiryDate(String detectedText){
       /* activity.expiryMonth = detectedText.split("/")[0];
        activity.expiryYear = detectedText.split("/")[1];*/
    }
}
