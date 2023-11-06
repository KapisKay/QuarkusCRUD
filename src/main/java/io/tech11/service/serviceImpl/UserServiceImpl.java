package io.tech11.service.serviceImpl;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.tech11.model.DTO.PasswordDTO;
import io.tech11.model.DTO.UserDTO;
import io.tech11.model.User;
import io.tech11.service.UserService;
import io.tech11.util.Utils;
import jakarta.ejb.ObjectNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public List<User> getAllUsers() {
        return User.findAll().list();
    }

    @Override
    public User getUserByUserId(UUID id) throws ObjectNotFoundException {
        User user = User.findById(id);
        if(user == null){
            throw new ObjectNotFoundException(String.format("No user with id %s found",id));
        }
        logger.info("querying  user with id {}", id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return User.find("email", email).firstResult();
    }

    @Transactional
    @Override
    public User createUser(UserDTO userDTO) {
        String userEmail = userDTO.getEmail();
        String userPassword = userDTO.getPassword();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Boolean emailIsValid = Utils.isEmailValid(userEmail);

        if(!emailIsValid) throw new RuntimeException(String.format("%s is an invalid email", userEmail));
        if(getUserByEmail(userEmail) != null) throw new RuntimeException(String.format("User with email %s already exists", userEmail));

        User user = mapper.map(userDTO, User.class);
        user.setPassword(Utils.hashPassword(userPassword));
        user.setBirthday(LocalDate.parse(userDTO.getBirthday(), formatter));
        user.setCreatedAt(LocalDateTime.now());
        user.persist();

        logger.info("User created successfully");
        return user;
    }

    @Transactional
    @Override
    public User updateUser(UUID id, UserDTO userDTO) throws ObjectNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        User user = getUserByUserId(id);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthday(LocalDate.parse(userDTO.getBirthday(), formatter));
        user.setUpdatedAt(LocalDateTime.now());
        user.persist();

        logger.info("User updated successfully");
        return user;
    }

    @Transactional
    @Override
    public User updateEmail(UUID id, String email) throws ObjectNotFoundException {
        User user = getUserByUserId(id);
        if(!Utils.isEmailValid(email)){
            throw new RuntimeException(String.format("%s is an invalid email", email));
        }
        if(getUserByEmail(email) != null) throw new RuntimeException(String.format("User with email %s already exists", email));
        user.setEmail(email);
        user.persist();
        logger.info("Email updated successfully");
        return user;
    }

    @Transactional
    @Override
    public User updatePassword(UUID id, PasswordDTO passwordDTO) throws ObjectNotFoundException {
        User user = getUserByUserId(id);
        if(!BcryptUtil.matches(passwordDTO.getCurrentPassword(), user.getPassword())){
            throw new RuntimeException("Your current password is incorrect");
        };
        user.setPassword(Utils.hashPassword(passwordDTO.getNewPassword()));
        user.persist();
        logger.info("Password updated successfully");
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) throws ObjectNotFoundException {
        User user = getUserByUserId(id);
        user.delete();
        logger.info("User with id {} deleted successfully", id);
    }
}
