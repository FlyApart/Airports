package com.airline.controller.messages;

import javax.validation.constraints.Size;

public class CreationMessage {
    private String successMessage;

    public CreationMessage(String message) {
        this.successMessage = message;
    }

    public String getMessage() {
        return successMessage;
    }
}
