package org.task.springboottodoapi.mapper;

import org.mapstruct.Mapper;
import org.task.springboottodoapi.dto.TodoDto;
import org.task.springboottodoapi.model.TodoModel;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoDto toDto(TodoModel todoModel);

    TodoModel toModel(TodoDto todoDto);
}
