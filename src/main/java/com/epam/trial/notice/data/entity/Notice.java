package com.epam.trial.notice.data.entity;

import com.epam.trial.notice.data.common.Status;
import com.epam.trial.notice.data.common.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@With
@Table(schema = "demo", name = "notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends Auditable<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "place")
  private String place;

  @Column(name = "personal_identification_code")
  private String pid;

  @Column(name = "case_present")
  private Boolean casePresent;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(name = "comment", columnDefinition = "TEXT")
  private String comment;

  @Column(name = "deadline_at")
  private LocalDateTime deadlineAt;

  public Notice(LocalDateTime date, String place, String pid, Boolean casePresent, Type type,
      Status status, String comment, LocalDateTime deadlineAt) {
    this.date = date;
    this.place = place;
    this.pid = pid;
    this.casePresent = casePresent;
    this.type = type;
    this.status = status;
    this.comment = comment;
    this.deadlineAt = deadlineAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    var that = (Notice) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
