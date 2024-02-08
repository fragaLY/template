package com.epam.trial.notice.data.payload;

import com.epam.trial.notice.data.common.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoticeModificationPayload(
    @NotNull(message = "The status of notice should be specified") Status status,
    @NotNull(message = "The feedback should be provided to user") String comment) {

}
