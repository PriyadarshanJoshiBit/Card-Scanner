package io.card.payment.firebase.parser;

import java.util.regex.Pattern;

import io.card.payment.firebase.accuracy.Tuner;
import io.card.payment.firebase.model.CardFeatures;
import io.card.payment.firebase.model.CardUC;

public class CvvParser implements ParserChain {

    ParserChain nextChain;
    Pattern cvvPattern = Pattern.compile("[0-9]{3}");

    CardFeatures _relatedFtr = CardFeatures.CVV;

    public CvvParser(){
        this.nextChain = null;
    }

    @Override
    public void parse(CardUC detectedCard, String detectedText) {

        if(detectedCard.shouldBeDetected(_relatedFtr)) {
            if(cvvPattern.matcher(detectedText).matches()){
                Tuner.getInstance().addCVV(detectedText);
                //detectedCard.getCard().setcVV(detectedText);
            }
            if(detectedCard.getMandatoryFieldsCaptured()){
                detectedCard.incrementAttempts(_relatedFtr);
            }

        }

    }
}
