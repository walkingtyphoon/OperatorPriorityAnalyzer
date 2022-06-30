package com.typhoon.exception;

import java.io.Serializable;

/**
 * 文法异常的父类
 *
 * @author walki
 * @Date 2022年5月18日14:40:49
 */
public class GrammarException extends Exception implements Serializable {

    private final String message;

    public GrammarException(String message) {
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        System.out.println(message);
    }
}
