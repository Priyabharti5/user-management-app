package com.priya.user_management_system.exception;

/**
 * Thrown when attempting to create a resource that already exists,
 * such as a duplicate username or email.
 */
public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String message){
        super(message);
    }
}
