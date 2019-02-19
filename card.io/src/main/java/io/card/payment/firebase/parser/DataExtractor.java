package io.card.payment.firebase.parser;


import io.card.payment.firebase.model.CardUC;

public class DataExtractor {

    ParserChain parser;
    public void extractData(CardUC detectedCard, String detectedText){
        this.parser.parse(detectedCard, detectedText);
    }
    public DataExtractor(){
        parser = new CardNumberParser();
    }
}