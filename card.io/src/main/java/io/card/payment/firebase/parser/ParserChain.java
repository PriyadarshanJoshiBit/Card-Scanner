package io.card.payment.firebase.parser;


import io.card.payment.firebase.model.DetectedCard;

public interface ParserChain {
    public void parse(DetectedCard detectedCard, String detectedText);

}
