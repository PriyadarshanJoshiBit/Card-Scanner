package io.card.payment.firebase.parser;



import android.util.Log;

import java.util.regex.Pattern;

import io.card.payment.firebase.model.CardFeatures;
import io.card.payment.firebase.model.CardUC;
import io.card.payment.firebase.accuracy.Tuner;
public class CardNumberParser implements ParserChain {
    ParserChain nextChain;
    Pattern cardNumberPattern = Pattern.compile("[0-9]{16}");
    CardFeatures _relatedFtr = CardFeatures.CARDNUM;

    @Override
    public void parse(CardUC detectedCard, String detectedText) {

        if(detectedCard.shouldBeDetected(_relatedFtr)){

            if(cardNumberPattern.matcher(detectedText).matches()){
                Log.i("Card-Parser","card NUmber Captured::"+ detectedText);
               //detectedCard.getCard().setCardNumber(detectedText);
               detectedCard.mandatoryFieldCaptured(_relatedFtr);
               Tuner.getInstance().addCardNumber(detectedText);
            }
           // detectedCard.incrementAttempts(_relatedFtr);
        }
        this.nextChain.parse(detectedCard, detectedText);

    }

    public CardNumberParser(){

        this.nextChain = new ExpiryDateParser();

    }
}
