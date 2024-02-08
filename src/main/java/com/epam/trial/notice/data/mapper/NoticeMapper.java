package com.epam.trial.notice.data.mapper;

import com.epam.trial.notice.data.common.Status;
import com.epam.trial.notice.data.entity.Notice;
import com.epam.trial.notice.data.payload.NoticeCreationPayload;
import com.epam.trial.notice.data.payload.NoticeModificationPayload;
import com.epam.trial.notice.data.response.NoticeResponse;
import jakarta.validation.constraints.NotNull;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between Notice entities and their associated payloads and
 * responses.
 */
@Component
public class NoticeMapper {

  /**
   * Converts a NoticeCreationPayload to a Notice entity.
   *
   * @param payload the NoticeCreationPayload containing the notice creation data
   * @return a new Notice instance populated with data from the payload
   * @throws NullPointerException if payload is null
   */
  @NotNull
  public Notice toEntity(
      @NotNull(message = "The creation payload should not be null") NoticeCreationPayload payload) {
    return new Notice(payload.date(), payload.place(), payload.pid(), payload.casePresent(),
        payload.type(), Status.IN_PROCESS, payload.comment(), LocalDateTime.now(Clock.systemUTC()));
  }

  /**
   * Updates and enriches an existing Notice entity with data from a NoticeModificationPayload.
   *
   * @param entity  the existing Notice entity to be updated
   * @param payload the NoticeModificationPayload containing the update data
   * @return the updated Notice entity
   * @throws NullPointerException if entity or payload is null
   */
  @NotNull
  public Notice enrich(@NotNull(message = "The entity should not be null") Notice entity,
      @NotNull(message = "The creation payload should not be null") NoticeModificationPayload payload) {
    entity.setStatus(payload.status());
    entity.setComment(payload.comment());
    return entity;
  }

  /**
   * Converts a Notice entity into a NoticeResponse DTO.
   *
   * @param entity the Notice entity to be converted
   * @return a NoticeResponse instance representing the entity
   * @throws NullPointerException if entity is null
   */
  @NotNull
  public NoticeResponse toResponse(
      @NotNull(message = "The entity should not be null") Notice entity) {
    return new NoticeResponse(entity.getId(), entity.getDate(), entity.getPlace(), entity.getPid(),
        entity.getCasePresent(), entity.getType(), entity.getStatus(), entity.getComment(),
        entity.getDeadlineAt(), entity.getCreatedBy(), entity.getUpdatedBy(), entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

}
