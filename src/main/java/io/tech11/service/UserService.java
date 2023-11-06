package io.tech11.service;

import io.tech11.model.DTO.EmailDTO;
import io.tech11.model.DTO.PasswordDTO;
import io.tech11.model.DTO.UserDTO;
import io.tech11.model.User;
import jakarta.ejb.ObjectNotFoundException;

import java.util.List;
import java.util.UUID;


public interface UserService {
    List<User> getAllUsers();
    User getUserByUserId(UUID id) throws ObjectNotFoundException;
    User getUserByEmail(String email) throws ObjectNotFoundException;
    User createUser(UserDTO userDTO) throws ObjectNotFoundException;
    User updateUser(UUID id, UserDTO userDTO) throws ObjectNotFoundException;
    User updateEmail(UUID id, String email) throws ObjectNotFoundException;
    User updatePassword(UUID id, PasswordDTO passwordDTO) throws ObjectNotFoundException;
    void deleteUser(UUID id) throws ObjectNotFoundException;

}
