package com.epam.trial.notice.data.response;

import com.epam.trial.notice.data.common.Status;
import com.epam.trial.notice.data.common.Type;
import java.time.LocalDateTime;
import java.util.UUID;

public record NoticeResponse(UUID id,
                             LocalDateTime date,
                             String place,
                             String pid,
                             Boolean casePresent,
                             Type type,
                             Status status,
                             String comment,
                             LocalDateTime deadlineAt,
                             String createdBy,
                             String updatedBy,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {

}
