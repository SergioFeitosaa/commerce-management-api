package br.com.sergio.customer_management_api.exception;

public class ProductInactiveException extends RuntimeException {

    public ProductInactiveException(String message) {
        super(message);
    }
}
