package com.sso.server.exception;

/**
 * @author wangteng05
 * @description: SsoException类
 * @date 2021/11/1 20:09
 */
public class SsoException extends RuntimeException{

    private static final long serialVersionUID = 1747216336678797660L;

    public SsoException(){
        super("SsoException");
    }

    public SsoException(String message){
        super(message);
    }
}
