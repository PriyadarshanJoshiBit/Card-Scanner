package io.card.payment.firebase.custom.exceptions;

public class MethodInvocationOnNullCardException extends Exception {

    public MethodInvocationOnNullCardException(){}

    public MethodInvocationOnNullCardException(String message){
        super(message);
    }
}
