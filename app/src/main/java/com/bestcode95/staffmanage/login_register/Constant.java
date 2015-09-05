package com.bestcode95.staffmanage.login_register;

/**
 * Created by mima123 on 15/8/13.
 */
public final class Constant {
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String RESPONSE_KEY = "resp";
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 0;
    public static final String REGISTER_SUCCESS = "success";
    public static final String REGISTER_EMAIL_NULL = "email is null";
    public static final String REGISTER_DATABASE_ERROR = "database error";
    public static final String REGISTER_ACCOUNT_EXISTS = "email is invalid";
    public static final String REGISTER_ACCOUNT_CAN_USE = "email is valid";
    public static final String REGISTER_CHECK_USER_URL = "http://leizbio.com:3000/mobile.php?act=checkUser";
    public static final String REGISTER_URL = "http://leizbio.com:3000/mobile.php?act=signUp";
    public static final String LOGIN_URL = "http://leizbio.com:3000/mobile.php?act=login";
    public static final String REGISTER_TAG = "register_tag";
    public static final String LOGIN_TAG = "login_tag";


    public static final String USERNAME_FLAG = "username_flag";
    public static final String PASSWORD_FLAG = "password_flag";
    public static final String BUNDLE_FLAG = "bundle_flag";
}
