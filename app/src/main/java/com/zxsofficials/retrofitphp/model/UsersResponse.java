package com.zxsofficials.retrofitphp.model;

import java.util.List;

public class UsersResponse {

    private Boolean error;
    private String message;
    private List<User> user;

    public UsersResponse(Boolean error, String message, List<User> user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getUser() {
        return user;
    }
}
