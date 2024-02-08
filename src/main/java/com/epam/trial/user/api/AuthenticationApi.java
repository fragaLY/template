package com.epam.trial.user.api;

import com.epam.trial.user.api.payload.LoginPayload;
import com.epam.trial.user.api.payload.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "authentication", description = "Authentication service API")
@Validated
public interface AuthenticationApi {

  @Operation(
      tags = {"authentication"},
      description = "Authenticates a user",
      summary = "Authentication")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "The user successfully authenticated"),
          @ApiResponse(responseCode = "400", description = "Request malformed or mandatory fields are missing"),
          @ApiResponse(responseCode = "500", description = "General application error")
      })
  @PostMapping(path = "/api/v1/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<LoginResponse> create(@Valid @RequestBody LoginPayload payload);

}
