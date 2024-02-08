package com.epam.trial.user.api.payload;

import java.util.UUID;

public record LoginResponse(UUID id, String token) {

}
