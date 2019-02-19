package io.card.payment.firebase.parser;


import io.card.payment.firebase.model.CardUC;

public interface ParserChain {
    public void parse(CardUC detectedCard, String detectedText);

}
