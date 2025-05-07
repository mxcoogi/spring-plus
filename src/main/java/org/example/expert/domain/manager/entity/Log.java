package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long todoId;

    private Long managerId;
}
