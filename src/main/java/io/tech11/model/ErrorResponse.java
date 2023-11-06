package io.tech11.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
}
