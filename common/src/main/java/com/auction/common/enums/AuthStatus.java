package com.auction.common.enums;

public enum AuthStatus {
    SUCCESS,
    FAILED,

    // Input / validation
    INVALID_INPUT,

    // Login
    WRONG_PASSWORD,
    INVALID_CREDENTIALS,
    USER_NOT_FOUND,
    ACCOUNT_LOCKED,
    ACCOUNT_DISABLED,

    // Register
    EMAIL_ALREADY_EXISTS,
    USERNAME_ALREADY_EXISTS,

    // Security
    UNAUTHORIZED,
    TOKEN_EXPIRED,

    // System
    SERVER_ERROR
}
