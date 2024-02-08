package com.epam.trial.notice.command.api;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.epam.trial.notice.command.service.NoticeCommandService;
import com.epam.trial.notice.data.payload.NoticeCreationPayload;
import com.epam.trial.notice.data.payload.NoticeModificationPayload;
import com.epam.trial.notice.data.response.NoticeResponse;
import com.epam.trial.notice.query.api.NoticeQueryController;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequiredArgsConstructor
public class NoticeCommandController implements NoticeCommandApi {

  private final NoticeCommandService service;

  @Override
  @PostMapping(path = "/api/v1/notices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('USER')")
  @Hidden
  public ResponseEntity<Void> create(@RequestBody NoticeCreationPayload payload) {
    var response = service.create(payload);
    var uri = fromMethodCall(
        on(NoticeQueryController.class).get(response.id(), null))
        .build()
        .toUri();
    return ResponseEntity.created(uri).build();
  }

  @Override
  @PutMapping(path = "/api/v1/notices/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICIAL', 'SYSTEM')")
  @Hidden
  public ResponseEntity<NoticeResponse> update(@RequestBody NoticeModificationPayload payload, @PathVariable("id") UUID id) {
    return ResponseEntity.ok(service.update(payload, id));
  }
}
