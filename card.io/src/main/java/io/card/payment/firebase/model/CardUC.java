package io.card.payment.firebase.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import io.card.payment.CreditCard;
import io.card.payment.DetectionInfo;

public class CardUC {

    private Bitmap img;
    private int orientation;
    private PrintedCard card;
    private boolean isPredicted = false;
    public int detectionAttempts;
    private List<CardFeatures> featuresToDetect = new ArrayList<CardFeatures>();
    public List<CardFeatures> mandatoryFeatures = new ArrayList<CardFeatures>();
    public  EnumMap<CardFeatures,Integer> detectionAttemptsMap = new EnumMap<CardFeatures, Integer>(CardFeatures.class);
    private boolean allMandatoryFieldsCaptured;


    public CardUC(){

    }

    public boolean shouldBeDetected(CardFeatures feature){
        return featuresToDetect.contains(feature);
    }

    public void enqueFeatureToBeDetected(CardFeatures features){
        if(! shouldBeDetected(features) ) featuresToDetect.add(features);
    }

    public void dequeFeatureToBeDetected(CardFeatures features){
        if( shouldBeDetected(features) ) featuresToDetect.remove(features);
    }

    public int getAttempts(CardFeatures feature){
        return detectionAttemptsMap.get(feature) == null ? 0 : detectionAttemptsMap.get(feature);
    }

    public boolean getMandatoryFieldsCaptured(){
        return  allMandatoryFieldsCaptured;
    }

    public void setMandatoryFieldsCaptured(boolean status){
        allMandatoryFieldsCaptured = status;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public PrintedCard getCard() {
        return card;
    }

    public void setCard(PrintedCard card) {
        this.card = card;
    }

    public Boolean getPredicted() {
        return isPredicted;
    }

    public void setPredicted(Boolean predicted) {
        isPredicted = predicted;
    }

    public int getDetectionAttempts() {
        return detectionAttempts;
    }

    public void setDetectionAttempts(int detectionAttempts) {
        this.detectionAttempts = detectionAttempts;
    }
    public void mandatoryFieldCaptured(CardFeatures feature){
        this.mandatoryFeatures.remove(feature);
        if(this.mandatoryFeatures.size() == 0){
            this.setMandatoryFieldsCaptured(true);
        }
    }
    public void incrementAttempts (CardFeatures feature){
        if(this.getAttempts(feature)==0){
            this.dequeFeatureToBeDetected(feature);
        }else {
            this.detectionAttemptsMap.put(feature, this.getAttempts(feature) - 1);
        }
    }

    public void reset(){
        this.img = null;
        this.card = null;
        this.isPredicted = false;
        allMandatoryFieldsCaptured =false;
    }


}