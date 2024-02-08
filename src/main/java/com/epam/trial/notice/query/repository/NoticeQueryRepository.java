package com.epam.trial.notice.query.repository;

import com.epam.trial.notice.data.entity.Notice;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoticeQueryRepository extends PagingAndSortingRepository<Notice, UUID>,
    JpaRepository<Notice, UUID> {

  Page<Notice> findAllByCreatedBy(Pageable pageable, String createdBy);

  Optional<Notice> findByIdAndCreatedBy(UUID id, String createdBy);
}
