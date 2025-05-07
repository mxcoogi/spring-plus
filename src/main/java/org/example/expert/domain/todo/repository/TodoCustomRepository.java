package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoCustomRepository {

    Page<Todo> findAllTodoWithWeatherAndDate(Pageable pageable, String weather, LocalDate startDate, LocalDate endDate);

    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    Page<TodoSearchResponse> searchTodos(Pageable pageable, String keyword, String manager, LocalDate startDate, LocalDate endDate);
}
