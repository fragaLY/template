package com.epam.trial.notice.command.service;

import com.epam.trial.notice.command.repository.NoticeCommandRepository;
import com.epam.trial.notice.data.exception.NotFoundException;
import com.epam.trial.notice.data.mapper.NoticeMapper;
import com.epam.trial.notice.data.payload.NoticeCreationPayload;
import com.epam.trial.notice.data.payload.NoticeModificationPayload;
import com.epam.trial.notice.data.response.NoticeResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer component responsible for executing commands related to Notices. It utilizes a
 * mapper for entity conversion and a repository for data persistence.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeCommandService {

  private final NoticeMapper mapper;
  private final NoticeCommandRepository repository;

  /**
   * Creates and persists a new Notice entity based on the provided payload.
   *
   * @param payload the payload containing the data for the new notice
   * @return a NoticeResponse DTO containing the details of the newly created notice
   */
  @Transactional
  public NoticeResponse create(NoticeCreationPayload payload) {
    log.debug("[NOTICE COMMAND SERVICE] New notice creation [{}] started", payload);
    var newNotice = mapper.toEntity(payload);
    var createdNotice = repository.save(newNotice);
    var response = mapper.toResponse(createdNotice);
    log.info("[NOTICE COMMAND SERVICE] New notice created [{}]", response);
    return response;
  }

  /**
   * Updates an existing Notice entity identified by the provided UUID and persists the changes.
   *
   * @param payload modification data to update the existing notice
   * @param id      the UUID of the notice to update
   * @return a NoticeResponse DTO containing the details of the updated notice
   * @throws NotFoundException if a notice with the provided UUID is not found
   */
  @Transactional
  public NoticeResponse update(NoticeModificationPayload payload, UUID id) {
    log.debug("[NOTICE COMMAND SERVICE] Notice [{}] modification [{}] started", id, payload);
    var notice = repository.findById(id).map(it -> mapper.enrich(it, payload))
        .orElseThrow(() -> new NotFoundException(id));
    var updatedNotice = repository.save(notice);
    var response = mapper.toResponse(updatedNotice);
    log.info("[NOTICE COMMAND SERVICE] Notice [{}] modificated [{}]", id, response);
    return response;
  }

}

