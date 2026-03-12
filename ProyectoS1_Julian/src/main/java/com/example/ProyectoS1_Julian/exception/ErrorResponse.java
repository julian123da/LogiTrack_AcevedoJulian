package com.example.ProyectoS1_Julian.exception;

import java.time.LocalDateTime;

public record ErrorResponse() {
    public ErrorResponse(LocalDateTime ignoredNow, int ignoredValue, String ignoredMessage, String ignoredResourceNotFound) {
        this();
    }
}
