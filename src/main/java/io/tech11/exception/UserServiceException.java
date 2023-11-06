package io.tech11.exception;

import io.tech11.model.ErrorResponse;
import io.tech11.model.UserResponse;
import io.tech11.util.Utils;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserServiceException implements ExceptionMapper<Exception> {
    ErrorResponse errorResponse = new ErrorResponse();
    @Override
    public Response toResponse(Exception exception) {
        UserResponse<Object> userResponse = Utils.createUserResponse(400,null,"Failed");
        if(exception instanceof InternalServerErrorException) {
            return Response.serverError().entity("Internal Server Error").build();
        }
        errorResponse.setCode(400);
        errorResponse.setMessage(exception.getMessage());
        userResponse.setError(errorResponse);
        return  Response.status(400).entity(userResponse).build();

    }
}
