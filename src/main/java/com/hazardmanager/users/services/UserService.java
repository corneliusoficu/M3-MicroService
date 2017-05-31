package com.hazardmanager.users.services;

import com.hazardmanager.users.models.User;

public interface UserService extends CrudService<User> {
 String getUserId(String username, String password);
}
