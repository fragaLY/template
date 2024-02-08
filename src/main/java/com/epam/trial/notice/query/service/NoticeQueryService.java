package com.epam.trial.notice.query.service;

import com.epam.trial.notice.data.entity.Notice;
import com.epam.trial.notice.data.exception.NotFoundException;
import com.epam.trial.notice.data.mapper.NoticeMapper;
import com.epam.trial.notice.data.response.NoticeResponse;
import com.epam.trial.notice.query.repository.NoticeQueryRepository;
import com.epam.trial.user.domain.Role;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer component responsible for handling queries related to Notices. It uses a mapper for
 * converting entities to DTOs and a repository for data retrieval.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeQueryService {

  private final NoticeMapper mapper;
  private final NoticeQueryRepository repository;

  /**
   * Retrieves a paginated list of NoticeResponse DTOs.
   *
   * @param pageable the pagination information
   * @return a paginated list of NoticeResponse DTOs
   */
  @Transactional(readOnly = true)
  public Page<NoticeResponse> get(Pageable pageable, Authentication authentication) {
    log.debug("[NOTICE QUERY SERVICE] Searching notices with page request [{}]", pageable);
    var isUser = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(String::toUpperCase)
        .anyMatch(it -> it.equals(Role.USER.name()));
    Page<Notice> page;
    if (isUser) {
      page = repository.findAllByCreatedBy(pageable, authentication.getName());
    } else {
      page = repository.findAll(pageable);
    }
    var response = page.map(mapper::toResponse);
    log.info("[NOTICE QUERY SERVICE] Notices with page request [{}] found [{}]", pageable,
        response.getTotalElements());
    return response;
  }

  /**
   * Retrieves a single NoticeResponse DTO based on the provided UUID.
   *
   * @param id the UUID of the notice to retrieve
   * @return a NoticeResponse DTO of the requested notice
   * @throws NotFoundException if a notice with the provided UUID is not found
   */
  @Transactional(readOnly = true)
  public NoticeResponse get(UUID id, Authentication authentication) {
    log.debug("[NOTICE QUERY SERVICE] Searching notice [{}]", id);
    var isUser = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(String::toUpperCase)
        .anyMatch(it -> it.equals(Role.USER.name()));
    Optional<Notice> notice;
    if (isUser) {
      notice = repository.findByIdAndCreatedBy(id, authentication.getName());
    } else {
      notice = repository.findById(id);
    }
    var response = notice.map(mapper::toResponse)
        .orElseThrow(() -> new NotFoundException(id));
    log.info("[NOTICE QUERY SERVICE] Notice [{}] found", id);
    return response;
  }
}
