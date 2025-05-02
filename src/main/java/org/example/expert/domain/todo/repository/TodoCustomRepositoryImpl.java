package org.example.expert.domain.todo.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.example.expert.domain.todo.entity.QTodo.todo;


@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Todo> findAllTodoWithWeatherAndDate(Pageable pageable, String weather, LocalDate startDate, LocalDate endDate) {
        BooleanBuilder builder = new BooleanBuilder();

        if (weather != null) {
            builder.and(todo.weather.eq(weather));
        }
        if (startDate != null) {
            builder.and(todo.modifiedAt.goe(startDate.atStartOfDay()));
        }
        if (endDate != null) {
            builder.and(todo.modifiedAt.loe(endDate.atTime(23, 59)));
        }
        List<Todo> todoList = jpaQueryFactory.selectFrom(todo)
                .where(
                        builder
                )
                .orderBy(todo.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long totalCount = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        long cnt = totalCount == null ? 0 : totalCount;
        return new PageImpl<>(todoList, pageable, cnt);
    }
}
