package com.example.userservice.mappers;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.userid", target = "id")
    @Mapping(source="user.login", target = "login")
    @Mapping(source="token", target="token")
    UserDto toUserDto(User user, String token);
}
