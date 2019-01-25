package io.card.payment.firebase.model;

import android.graphics.Bitmap;

import io.card.payment.firebase.custom.exceptions.MethodInvocationOnNullCardException;

public interface Card {

    public boolean isValidCard();
    public String getCardNumber() throws MethodInvocationOnNullCardException;
    public int getExpiryMonth() throws MethodInvocationOnNullCardException;
    public int getExpiryYear() throws MethodInvocationOnNullCardException;
    public int getCVV() throws MethodInvocationOnNullCardException;
    public Bitmap getImage() throws MethodInvocationOnNullCardException;

}
