package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="notification")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@DynamicInsert
public class Notification {

	@Id @GeneratedValue
	private Long id;

	private String title;

	private String link;

	private String wording;

	private boolean checked;

	@ManyToOne
	private Member member;

	private LocalDateTime createdDateTime;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

}
