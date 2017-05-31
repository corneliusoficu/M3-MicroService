package com.hazardmanager.users.services;

import com.hazardmanager.users.custom_exceptions.PasswordMismatchException;
import com.hazardmanager.users.custom_exceptions.UsernameNotRegisteredException;
import com.hazardmanager.users.models.User;

public interface UserService extends CrudService<User> {
 String getUserId(String username, String password) throws UsernameNotRegisteredException, PasswordMismatchException;
}
