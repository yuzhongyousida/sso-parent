package com.sso.server.exception;

/**
 * @author wangteng05
 * @description: AddUserException类
 * @date 2021/11/1 17:10
 */
public class AddUserException extends RuntimeException{
    private static final long serialVersionUID = 7591420322848927969L;

    public AddUserException() {
        super("AddUser Exception");
    }

    public AddUserException(String message) {
        super(message);
    }

}
