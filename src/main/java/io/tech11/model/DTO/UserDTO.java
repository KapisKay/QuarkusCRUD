package io.tech11.model.DTO;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDTO implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
