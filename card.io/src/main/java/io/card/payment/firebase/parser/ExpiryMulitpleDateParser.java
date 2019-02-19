package io.card.payment.firebase.parser;

import java.util.regex.Pattern;

import io.card.payment.firebase.accuracy.Tuner;
import io.card.payment.firebase.model.CardFeatures;
import io.card.payment.firebase.model.CardUC;

public class ExpiryMulitpleDateParser implements ParserChain {


    Pattern expiryDatePatternCompbined = Pattern.compile("[0-9]{2}/[0-9]{4}/[0-9]{2}");
    ParserChain nextLink;
    CardFeatures _relatedFtr = CardFeatures.EXPIRY;
    private int attemptsAllowed;
    public ExpiryMulitpleDateParser(){

        this.attemptsAllowed = CardFeatures.EXPIRY.atmtsToFindAfterMndtryFlds;
        nextLink = new CvvParser();
    }

    @Override
    public void parse(CardUC detectedCard, String detectedText) {

        if(detectedCard.shouldBeDetected(_relatedFtr)){
            if(expiryDatePatternCompbined.matcher(detectedText).matches()){

                String expiryDt = parseExpiry(detectedText);
                Tuner.getInstance().addExpDt(expiryDt);
                //detectedCard.getCard().setExpiryDate(expiryDt);
            }
            if(detectedCard.getMandatoryFieldsCaptured()){
                detectedCard.incrementAttempts(_relatedFtr);
            }

        }

        this.nextLink.parse(detectedCard,detectedText);
    }

    private String parseExpiry(String rawText) {

        Integer date[] = new Integer[4];
        int expiryMonth, expiryYear;

        date[0] = Integer.parseInt(rawText.split("/")[0]);
        date[1] = Integer.parseInt(rawText.split("/")[1].substring(0, 2));
        date[2] = Integer.parseInt(rawText.split("/")[1].substring(2));
        date[3] = Integer.parseInt(rawText.split("/")[2]);
        if (date[1] > date[3]) {
            expiryMonth = date[0];
            expiryYear = date[1];
        } else if (date[1] < date[3]) {
            expiryMonth = date[2];
            expiryYear = date[3];
        } else {
            if (date[0] < date[2]) {
                expiryMonth = date[2];
                expiryYear = date[3];
            } else {
                expiryMonth = date[0];
                expiryYear = date[1];
            }
        }
        return "" +
                (expiryMonth < 10 ? "0" + expiryMonth : expiryMonth ) +
                "/" +
                (expiryYear < 10 ? "0" + expiryYear : expiryYear );
    }
}
