package br.com.sergio.customer_management_api.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String menssage) {
        super(menssage);
    }
}
