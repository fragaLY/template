package com.epam.trial.notice.data.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

  public NotFoundException(UUID id) {
    super("The entity with id [%s] was not found".formatted(id));
  }
}
