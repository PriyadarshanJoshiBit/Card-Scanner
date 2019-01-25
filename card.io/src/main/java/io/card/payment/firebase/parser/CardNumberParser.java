package io.card.payment.firebase.parser;



import java.util.regex.Pattern;

import io.card.payment.firebase.model.DetectedCard;

public class CardNumberParser implements ParserChain {
    ParserChain nextChain;
    Pattern cardNumberPattern = Pattern.compile("[0-9]{16}");

    @Override
    public void parse(DetectedCard detectedCard, String detectedText) {

        if(cardNumberPattern.matcher(detectedText).matches()){
            /*activity.setCardNumberDetected(true);
            activity.cardNumber = detectedText;
*/
        }
            this.nextChain.parse(detectedCard, detectedText);

    }

    public CardNumberParser(){

        this.nextChain = new ExpiryDateParser();

    }
}
