package com.mmihaylov.rest.services.impl;

import com.mmihaylov.rest.RestServicesException;
import com.mmihaylov.rest.resources.model.UserEntity;
import com.mmihaylov.rest.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by mmihaylov on 4/17/16.
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    public UserEntity getUser(int id) {
        LOG.debug("Find a user with id: %d", id);
        if(id <= 0) {
            throw new RestServicesException("Invalid negative id.");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Dumas");
        userEntity.setAddress("Sofia");
        userEntity.setAge(29);
        return userEntity;
    }
}
