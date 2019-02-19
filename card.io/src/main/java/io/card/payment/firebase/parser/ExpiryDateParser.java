package io.card.payment.firebase.parser;

import java.util.regex.Pattern;

import io.card.payment.firebase.accuracy.Tuner;
import io.card.payment.firebase.model.CardFeatures;
import io.card.payment.firebase.model.CardUC;

public class ExpiryDateParser implements ParserChain {

    private ParserChain nextChain;
    private Pattern expiryDatePattern = Pattern.compile("[0-9]{2}/[0-9]{2}");
    private CardFeatures _relatedFtr = CardFeatures.EXPIRY;
    private int attemptsAllowed;
    public ExpiryDateParser(){
        this.attemptsAllowed = CardFeatures.EXPIRY.atmtsToFindAfterMndtryFlds;
        this.nextChain = new ExpiryMulitpleDateParser();
    }

    @Override
    public void parse(CardUC detectedCard, String detectedText) {

        if(detectedCard.shouldBeDetected(_relatedFtr)){
            if(detectedCard.getMandatoryFieldsCaptured()) {
                detectedCard.incrementAttempts(_relatedFtr);

                if (expiryDatePattern.matcher(detectedText).matches()) {
                    //detectedCard.getCard().setExpiryDate(detectedText);
                    Tuner.getInstance().addExpDt(detectedText);
                }
            }

        }
            this.nextChain.parse(detectedCard,detectedText);
    }


}
