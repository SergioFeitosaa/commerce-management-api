package br.com.sergio.customer_management_api.exception;

public class OrderAlreadyCanceledException extends RuntimeException {

    public OrderAlreadyCanceledException(String message) {
        super(message);
    }
}
