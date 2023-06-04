package ru.ilmira.myProject.service.dto;

import ru.ilmira.myProject.persist.model.User;

public interface UserMapper {

    UserDto fromUser(User user);
}
