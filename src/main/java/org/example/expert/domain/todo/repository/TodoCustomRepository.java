package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoCustomRepository {

    Page<Todo> findAllTodoWithWeatherAndDate(Pageable pageable, String weather, LocalDate startDate, LocalDate endDate);

    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
