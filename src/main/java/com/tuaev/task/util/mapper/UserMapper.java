package com.tuaev.task.util.mapper;

import com.tuaev.task.dto.UserDTO;
import com.tuaev.task.entity.User;

public class UserMapper {

    public UserMapper() {
    }

    public static User toUser(UserDTO userDTOr) {
        return new User(userDTOr.getUserName(), userDTOr.getLastName(), userDTOr.getAge());
    }
}
