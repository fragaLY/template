package com.epam.trial.notice.data.payload;

import com.epam.trial.notice.data.common.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NoticeCreationPayload(
    @NotNull(message = "The date of notice should not be null") LocalDateTime date,
    @NotBlank(message = "The place of notice should not be blank")
    @Size(min = 1, max = 255, message = "The place description length should be between 1 and 255 symbols") String place,
    @NotBlank(message = "The personal identifier should not be blank")
    @Size(min = 1, max = 255, message = "The personal identifier length should be between 1 and 255 symbols")String pid,
    boolean casePresent,
    @NotNull(message = "The type of notice should not be null") Type type,
    @Nullable String comment) {

}
