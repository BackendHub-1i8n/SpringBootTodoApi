package org.task.springboottodoapi.mapper;

import org.mapstruct.Mapper;
import org.task.springboottodoapi.dto.UserDto;
import org.task.springboottodoapi.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel user);
    UserModel toModel(UserDto userDto);
}
