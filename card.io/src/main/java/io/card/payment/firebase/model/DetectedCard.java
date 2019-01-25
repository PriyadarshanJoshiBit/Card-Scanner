package io.card.payment.firebase.model;

import android.graphics.Bitmap;

import java.util.EnumMap;

public class DetectedCard {

    private Bitmap img;
    private int orientation;
    private Card card;
    private Boolean isPredicted;
    private int detectionAttempts;


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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
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
}
