package com.epam.trial.user.api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record LoginPayload(@NotNull(message = "The user's id is mandatory") UUID id,
                           @NotBlank(message = "The user's password should not be blank") String password) {

}
