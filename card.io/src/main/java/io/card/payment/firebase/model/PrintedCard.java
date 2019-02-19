package io.card.payment.firebase.model;

import android.graphics.Bitmap;

import io.card.payment.firebase.custom.exceptions.MethodInvocationOnNullCardException;

public class PrintedCard  {

    private String cardNumber;
    private String expiryDate;
    private String cVV;
    private Bitmap cardImage;


    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getcVV() {
        return cVV;
    }

    public Bitmap getCardImage() {
        return cardImage;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setcVV(String cVV) {
        this.cVV = cVV;
    }

    public void setCardImage(Bitmap cardImage) {
        this.cardImage = cardImage;
    }



    public boolean isValidCard() {
        return true;
    }



}
