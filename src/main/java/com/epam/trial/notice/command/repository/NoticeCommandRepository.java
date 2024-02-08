package com.epam.trial.notice.command.repository;

import com.epam.trial.notice.data.entity.Notice;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCommandRepository extends JpaRepository<Notice, UUID> {

}
