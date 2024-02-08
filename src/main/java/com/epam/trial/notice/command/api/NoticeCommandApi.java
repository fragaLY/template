package com.epam.trial.notice.command.api;

import com.epam.trial.notice.data.payload.NoticeCreationPayload;
import com.epam.trial.notice.data.payload.NoticeModificationPayload;
import com.epam.trial.notice.data.response.NoticeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "notice", description = "Notice Command service API")
@Validated
public interface NoticeCommandApi {

  @Operation(
      tags = {"notice", "notice-creation"},
      description = "Creates a new notice by logged in user",
      summary = "Creates a notice")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "201", description = "The notice successfully created"),
          @ApiResponse(responseCode = "400", description = "Request malformed or mandatory fields are missing"),
          @ApiResponse(responseCode = "500", description = "General application error")
      })
  @PostMapping(path = "/api/v1/notices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> create(@Valid @RequestBody NoticeCreationPayload payload);

  @Operation(
      tags = {"notice", "notice-modification"},
      description = "Updates a notice by logged in user",
      summary = "Updates a notice")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "The notice successfully modified"),
          @ApiResponse(responseCode = "400", description = "Request malformed or mandatory fields are missing"),
          @ApiResponse(responseCode = "500", description = "General application error")
      })
  @PutMapping(path = "/api/v1/notices/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<NoticeResponse> update(@Valid @RequestBody NoticeModificationPayload payload,
      @PathVariable("id") UUID id);

}
