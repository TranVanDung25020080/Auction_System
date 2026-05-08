package com.auction.common.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName) {
        super(resourceName + " không tồn tại trong hệ thống!");
    }
}