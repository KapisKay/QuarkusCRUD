package io.tech11.restController;

import io.tech11.model.DTO.EmailDTO;
import io.tech11.model.DTO.PasswordDTO;
import io.tech11.model.DTO.UserDTO;
import io.tech11.model.UserResponse;
import io.tech11.model.User;
import io.tech11.service.UserService;
import io.tech11.util.Utils;
import jakarta.ejb.ObjectNotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Path("api/v1/users")
public class UserRestController {

    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();
    @Inject
    public UserRestController(UserService userService){
        this.userService = userService;
    }
    @GET
    public Response getUsers(){
        List<User> allUsers = userService.getAllUsers();
        return  Response.status(Response.Status.OK).
                entity(allUsers).type(MediaType.APPLICATION_JSON).build();
    }
    @GET
    @Path("{id}")
    public UserResponse<User> getUser(@PathParam("id")UUID id) throws ObjectNotFoundException {
        User user = userService.getUserByUserId(id);
        return Utils.createUserResponse(200, user, "");
    }
    @GET
    @Path("email/{email}")
    public UserResponse<User> getUserByEmail(@PathParam("email")String email) throws ObjectNotFoundException {
        User user = userService.getUserByEmail(email);
        return Utils.createUserResponse(200, user,"");
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public UserResponse<User> addUser(UserDTO userDTO) throws ObjectNotFoundException {
        mapper.map(userDTO, User.class);
        User user = userService.createUser(userDTO);
        return  Utils.createUserResponse(201,user,"user created successfully");
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public  UserResponse<User> updateUser(@PathParam("id") UUID id, UserDTO userDTO) throws ObjectNotFoundException {
        User user = userService.updateUser(id, userDTO);
        mapper.map(user, userDTO);
        return  Utils.createUserResponse(200, user, "user updated successfully");
    }

    @PUT
    @Path("reset-email/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public  UserResponse<User> updateEmail(@PathParam("id") UUID id, EmailDTO email) throws ObjectNotFoundException {
        User user = userService.updateEmail(id, email.getEmail());
        return  Utils.createUserResponse(200, user, "user email updated successfully");
    }

    @PUT
    @Path("reset-password/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public  UserResponse<User> resetPassword(@PathParam("id") UUID id, PasswordDTO passwordDTO) throws ObjectNotFoundException {
        User user = userService.updatePassword(id, passwordDTO);
        return  Utils.createUserResponse(200, user, "user password updated successfully");
    }

    @DELETE
    @Path("{id}")
    public UserResponse<Object> deleteUser(@PathParam("id") UUID id) throws ObjectNotFoundException {
        userService.deleteUser(id);
        return Utils.createUserResponse(200, null, "user deleted successfully");
    }

}
