package br.com.sergio.customer_management_api.exception;

public class PendingPaymentAlreadyExistsException extends RuntimeException {

    public PendingPaymentAlreadyExistsException(String message) {
        super(message);
    }
}
