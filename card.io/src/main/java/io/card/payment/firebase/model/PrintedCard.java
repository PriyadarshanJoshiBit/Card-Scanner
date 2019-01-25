package io.card.payment.firebase.model;

import android.graphics.Bitmap;

import io.card.payment.firebase.custom.exceptions.MethodInvocationOnNullCardException;

public class PrintedCard implements Card {

    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cVV;
    private Bitmap cardImage;

    @Override
    public boolean isValidCard() {
        return true;
    }

    @Override
    public String getCardNumber() throws MethodInvocationOnNullCardException {
        return this.cardNumber;
    }

    @Override
    public int getExpiryMonth() throws MethodInvocationOnNullCardException {
        return this.expiryMonth;
    }

    @Override
    public int getExpiryYear() throws MethodInvocationOnNullCardException {
        return this.expiryYear;
    }

    @Override
    public int getCVV() throws MethodInvocationOnNullCardException {
        return this.cVV;
    }

    @Override
    public Bitmap getImage() throws MethodInvocationOnNullCardException {
        return this.cardImage;
    }
}
