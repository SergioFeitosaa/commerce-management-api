package br.com.sergio.customer_management_api.exception;

public class OrderHasPaymentHistoryException extends RuntimeException {

    public OrderHasPaymentHistoryException(String message) {
        super(message);
    }
}
