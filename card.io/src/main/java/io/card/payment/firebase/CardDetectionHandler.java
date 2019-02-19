package io.card.payment.firebase;

import android.util.Log;

import io.card.payment.CreditCard;
import io.card.payment.DetectionInfo;
import io.card.payment.firebase.accuracy.Tuner;
import io.card.payment.firebase.model.CardFeatures;
import io.card.payment.firebase.model.CardUC;
import io.card.payment.firebase.model.PrintedCard;

/**
 * The purpose of this class is to maintain the detection attempts of Card Under Construction Object
 * This class is also responsible for transforming Card Under Construction Object to Detection Info object used by CardIO Activity
 *
 */

public class CardDetectionHandler {
  private CardUC cardUC;

    public CardUC getCardUCInstance(){
        if(this.cardUC == null){
            this.cardUC = new CardUC();
            initializeCardProcessing();
        }
        return this.cardUC;
    }
    /*
    * This method initalizes Card Under Construction project as per the configuration in CardFeatures Enum*/
    public void initializeCardProcessing(){
        this.cardUC.reset();
        for(CardFeatures feature : CardFeatures.values()){
            if(feature.isMandatory ) this.cardUC.mandatoryFeatures.add(feature);
            this.cardUC.detectionAttemptsMap.put(feature, feature.atmtsToFindAfterMndtryFlds);
            this.cardUC.enqueFeatureToBeDetected(feature);
            if(feature.atmtsToFindAfterMndtryFlds > this.cardUC.getDetectionAttempts()){
                this.cardUC.setDetectionAttempts(feature.atmtsToFindAfterMndtryFlds);
            }
        }
    }


    public void decrementDetectionAttempts(){
        if(this.cardUC.getMandatoryFieldsCaptured()){
            this.cardUC.detectionAttempts--;
        }

    }
    public DetectionInfo getDinfo(){


        DetectionInfo detectionInfo = new DetectionInfo();
        detectionInfo.complete= true;
        detectionInfo.topEdge = true;
        detectionInfo.leftEdge = true;
        detectionInfo.rightEdge = true;
        if(this.cardUC.getCard().getExpiryDate()!=null) {
            detectionInfo.expiry_month = Integer.parseInt(this.cardUC.getCard().getExpiryDate().split("/")[0]);
            detectionInfo.expiry_year = Integer.parseInt(this.cardUC.getCard().getExpiryDate().split("/")[1]);
        }
        detectionInfo.prediction = toIntArray(this.cardUC.getCard().getCardNumber());
        freeResources();
        return detectionInfo;
    }
    public void setDetectedCard(CreditCard card){

        card.expiryMonth = Integer.parseInt(this.cardUC.getCard().getExpiryDate().split("/")[0]);
        card.expiryYear = Integer.parseInt(this.cardUC.getCard().getExpiryDate().split("/")[1]);
        card.cardNumber = this.cardUC.getCard().getCardNumber();
        card.cvv = this.cardUC.getCard().getcVV();

    }

    public int[] toIntArray(String string){

        int[] cardNumber =  new int[string.length()];
        char[] charArray = string.toCharArray() ;
        for(int index= 0 ; index < charArray.length; index++){
            cardNumber[index] = Character.getNumericValue(charArray[index]);
        }
        return cardNumber;
    }



    public void verifyAttempts(){
      //  Log.i("Card-Handler","Detection-Attempts::"+ this.cardUC.getDetectionAttempts());
        if (this.cardUC.getDetectionAttempts() == 0) {
              buildPredictedCard();

        }
        decrementDetectionAttempts();

    }
    public void buildPredictedCard(){
        cardUC.setPredicted(true);
        PrintedCard printedCard = new PrintedCard();
        printedCard.setCardNumber(Tuner.getInstance().mostlyOccuringCardNumber());
        printedCard.setExpiryDate(Tuner.getInstance().mostlyOccuringExpiryDate());
        printedCard.setcVV(Tuner.getInstance().mostlyOccuringCVV());
        cardUC.setCard(printedCard);

    }

    public void freeResources(){
        //cardUC.setMandatoryFieldsCaptured(false);
        initializeCardProcessing();
        Tuner.getInstance().nullify();
    }
}
