package com.abaco.validators;

public class FormValidatorException extends RuntimeException {

    private int code;

    public FormValidatorException(String message) {
        super(message);
    }

    public FormValidatorException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
