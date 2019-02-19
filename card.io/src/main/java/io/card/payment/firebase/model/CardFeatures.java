package io.card.payment.firebase.model;

import java.util.ArrayList;
import java.util.List;

import io.card.payment.firebase.parser.ParserChain;

public enum CardFeatures {
    //Mandatory,attempts
    CARDNUM("Card Number",true, 0),
    CVV("CVV" , false, 3),
    EXPIRY("Expiry Date", false,5);

    public String name;
    public boolean isMandatory;
    public int atmtsToFindAfterMndtryFlds;

    CardFeatures(String name, boolean isMandatory, int atmtsToFindAfterMndtryFlds ){
        this.isMandatory = isMandatory;
        this.atmtsToFindAfterMndtryFlds = atmtsToFindAfterMndtryFlds;
        this.name = name;
    }


   public List<CardFeatures> getMandatoryFeatures(){
        List<CardFeatures> mandatoryFtrs = new ArrayList<CardFeatures>() ;
        for(CardFeatures feature : CardFeatures.values()){
            if (feature.isMandatory) mandatoryFtrs.add(feature);
        }
        return mandatoryFtrs;
   }
}
