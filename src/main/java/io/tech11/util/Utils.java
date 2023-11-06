package io.tech11.util;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.tech11.model.UserResponse;

public class Utils {

    private static  final String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static Boolean isEmailValid(String email){
        return email.matches(emailRegex);
    }

    public static String hashPassword(String password){
        return BcryptUtil.bcryptHash(password);
    }

    public static <T>UserResponse<T> createUserResponse(int code, T user, String message) {
        UserResponse<T> userResponse = new UserResponse<>();
        userResponse.setCode(code);
        userResponse.setMessage(message);
        userResponse.setData(user);
        return userResponse;
    }

}
