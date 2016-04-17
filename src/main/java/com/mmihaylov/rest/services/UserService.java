package com.mmihaylov.rest.services;

import com.mmihaylov.rest.resources.model.UserEntity;

public interface UserService {

    UserEntity getUser(int id);
}
