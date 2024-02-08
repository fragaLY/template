package com.epam.trial.notice.cron.repository;

import com.epam.trial.notice.data.common.Status;
import com.epam.trial.notice.data.entity.Notice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCompensatoryRepository extends JpaRepository<Notice, UUID> {

  List<Notice> findAllByDeadlineAtBeforeAndStatus(LocalDateTime deadlineAt, Status status);
}
