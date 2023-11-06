package io.tech11.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;
    private ErrorResponse error;


}
