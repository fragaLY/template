package com.epam.trial.notice.query.api;

import com.epam.trial.notice.data.response.NoticeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "notice", description = "Notice Query service API")
@Validated
public interface NoticeQueryApi {

  @Operation(
      tags = {"notice", "notice-retrieving"},
      description = "Gets all notices which are applicable for a logged in user",
      summary = "Gets notices")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "The notices successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Request malformed or mandatory fields are missing"),
          @ApiResponse(responseCode = "500", description = "General application error")
      })
  @GetMapping(path = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<NoticeResponse>> get(Pageable pageable, Authentication authentication);

  @Operation(
      tags = {"notice", "notice-retrieving"},
      description = "Gets notice that is applicable for a logged in user",
      summary = "Gets notice")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "The notice successfully retrieved"),
          @ApiResponse(responseCode = "400", description = "Request malformed or mandatory fields are missing"),
          @ApiResponse(responseCode = "500", description = "General application error")
      })
  @GetMapping(path = "/api/v1/notices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<NoticeResponse> get(@PathVariable("id") UUID id, Authentication authentication);

}
