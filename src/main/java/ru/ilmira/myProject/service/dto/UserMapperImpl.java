package ru.ilmira.myProject.service.dto;

import org.springframework.stereotype.Component;
import ru.ilmira.myProject.persist.model.User;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .condition(user.getCondition().name())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()))
                .build();
    }
}
