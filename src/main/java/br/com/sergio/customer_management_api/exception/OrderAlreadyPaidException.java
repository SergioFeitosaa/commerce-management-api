package br.com.sergio.customer_management_api.exception;

public class OrderAlreadyPaidException extends RuntimeException {

    public OrderAlreadyPaidException(String message) {
        super(message);
    }
}
