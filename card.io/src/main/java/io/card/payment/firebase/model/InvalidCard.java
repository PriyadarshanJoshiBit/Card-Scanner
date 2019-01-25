package io.card.payment.firebase.model;

import android.graphics.Bitmap;
import io.card.payment.firebase.custom.exceptions.MethodInvocationOnNullCardException;

public class InvalidCard implements Card {


    @Override
    public boolean isValidCard() {
        return false;
    }

    @Override
    public String getCardNumber()  throws MethodInvocationOnNullCardException {
        throw new MethodInvocationOnNullCardException("Cannot provide card details of a null card");
    }

    @Override
    public int getExpiryMonth() throws MethodInvocationOnNullCardException {
        throw new MethodInvocationOnNullCardException("Cannot provide card details of a null card");
    }

    @Override
    public int getExpiryYear()  throws MethodInvocationOnNullCardException {
        throw new MethodInvocationOnNullCardException("Cannot provide card details of a null card");
    }

    @Override
    public int getCVV()  throws MethodInvocationOnNullCardException {
        throw new MethodInvocationOnNullCardException("Cannot provide card details of a null card");
    }

    @Override
    public Bitmap getImage()  throws MethodInvocationOnNullCardException {
        throw new MethodInvocationOnNullCardException("Cannot provide card details of a null card");
    }
}
