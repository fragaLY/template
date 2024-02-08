package com.epam.trial.notice.query.api;

import com.epam.trial.notice.data.response.NoticeResponse;
import com.epam.trial.notice.query.service.NoticeQueryService;
import io.swagger.v3.oas.annotations.Hidden;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeQueryController implements NoticeQueryApi {

  private final NoticeQueryService service;

  @Override
  @GetMapping(path = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Page<NoticeResponse>> get(
      @PageableDefault(sort = {"deadlineAt",
          "status"}, direction = Direction.DESC) Pageable pageable,
       Authentication authentication) {
    return ResponseEntity.ok(service.get(pageable, authentication));
  }

  @Override
  @GetMapping(path = "/api/v1/notices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Hidden
  public ResponseEntity<NoticeResponse> get(@PathVariable("id") UUID id,
      Authentication authentication) {
    return ResponseEntity.ok(service.get(id, authentication));
  }
}
