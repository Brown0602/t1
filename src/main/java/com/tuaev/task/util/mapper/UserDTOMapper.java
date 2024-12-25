package com.tuaev.task.util.mapper;

import com.tuaev.task.dto.UserDTO;
import com.tuaev.task.entity.User;

public class UserDTOMapper {

    public UserDTOMapper() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUserName(), user.getLastName(), user.getAge());
    }
}
