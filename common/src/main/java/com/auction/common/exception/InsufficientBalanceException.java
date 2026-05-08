package com.auction.common.exception;

public class InsufficientBalanceException extends BaseException {
    public InsufficientBalanceException() {
        super("Số dư tài khoản không đủ để thực hiện đấu giá này!");
    }
}