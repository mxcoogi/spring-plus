package org.example.expert.domain.todo.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;


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

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo res = jpaQueryFactory.selectFrom(todo)
                .join(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();
        return Optional.ofNullable(res);
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(Pageable pageable,
                                                String keyword, String managerNickName,
                                                LocalDate startDate, LocalDate endDate) {

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(todo.title.containsIgnoreCase(keyword));
        }

        if (managerNickName != null && !managerNickName.isBlank()) {
            builder.and(manager.user.nickName.containsIgnoreCase(managerNickName));
        }

        if (startDate != null) {
            builder.and(todo.createdAt.goe(startDate.atStartOfDay()));
        }

        if (endDate != null) {
            builder.and(todo.createdAt.loe(endDate.atTime(23, 59, 59)));
        }

        // 메인 데이터 + 카운트 쿼리
        List<TodoSearchResponse> content = jpaQueryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.id,
                        todo.title,
                        manager.id.countDistinct(),
                        comment.id.countDistinct(),
                        todo.createdAt,
                        todo.modifiedAt
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .leftJoin(manager.user, user) // 매니저 닉네임 필터링에 필요
                .where(builder)
                .groupBy(todo.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(todo.createdAt.desc())
                .fetch();

        // 전체 개수 쿼리
        Long total = jpaQueryFactory
                .select(todo.countDistinct())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }


}
