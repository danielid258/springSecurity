package com.daniel.security.exception;

/**
 * on 5/25/2018.
 */
public class UserNotExistException extends RuntimeException {
    private Integer id;

    public UserNotExistException(Integer id) {
        super("user not exist exception");
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
