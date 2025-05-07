package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.entity.Log;
import org.example.expert.domain.manager.repository.LogRepository;
import org.example.expert.domain.user.entity.CustomUserDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerLogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveManagerLog(CustomUserDetail userDetail, long todoId, ManagerSaveRequest managerSaveRequest) {
        Long userId = userDetail.getId();
        Long managerId = managerSaveRequest.getManagerUserId();
        Log log = Log.builder()
                .userId(userId)
                .todoId(todoId)
                .managerId(managerId)
                .build();
        logRepository.save(log);
    }

}
