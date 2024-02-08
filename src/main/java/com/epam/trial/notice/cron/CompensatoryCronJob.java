package com.epam.trial.notice.cron;

import com.epam.trial.notice.cron.repository.NoticeCompensatoryRepository;
import com.epam.trial.notice.data.common.Status;
import com.epam.trial.user.domain.Role;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableAsync
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CompensatoryCronJob {

  @Value("${application.deadline}")
  private long deadline;
  private final NoticeCompensatoryRepository repository;

  @Bean("compensatoryAsyncExecutor")
  public ExecutorService getAsyncExecutor() {
    return Executors.newSingleThreadExecutor();
  }

  @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
  public void compensatoryCronJob() {
    log.info("[NOTICE COMPENSATORY SERVICE] Compensatory job started");
    compensate().whenComplete((result, exception) ->
    {
      if (exception != null) {
        log.error("[NOTICE COMPENSATORY SERVICE] Compensatory job completed with exception [{}]",
            exception.getMessage());
      } else {
        log.info(
            "[NOTICE COMPENSATORY SERVICE] Compensatory job completed successfully.");
      }
    });
  }

  @Async("compensatoryAsyncExecutor")
  public CompletableFuture<Void> compensate() {
    return CompletableFuture.runAsync(() -> {
      var notices = repository.findAllByDeadlineAtBeforeAndStatus(
              LocalDateTime.now(Clock.systemUTC()).minusMinutes(deadline), Status.IN_PROCESS)
          .stream()
          .peek(it -> {
            it.setDeadlineAt(it.getDeadlineAt().plusMinutes(deadline));
            it.setUpdatedBy(Role.SYSTEM.name());
            it.setUpdatedAt(LocalDateTime.now(Clock.systemUTC()));
            it.setComment("The notice had been postponed by system");
          })
          .toList();
      log.info("[NOTICE COMPENSATORY SERVICE] Unreviewed notices [{}] found", notices.size());
      repository.saveAll(notices);
      repository.flush();
    });
  }
}
