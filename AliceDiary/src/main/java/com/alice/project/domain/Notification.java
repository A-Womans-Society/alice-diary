package com.alice.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(of = "num")
@DynamicInsert
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATION_SEQ_GENERATOR")
	@SequenceGenerator(name = "NOTIFICATION_SEQ_GENERATOR", sequenceName = "SEQ_NOTIFICATION_NUM", initialValue = 1, allocationSize = 1)
	@Column(name = "notification_num")
	private Long num;

	private String title;

	private String link;

	private String comment;

	private boolean checked;

	@ManyToOne
	@JsonBackReference
	private Member member;

	private LocalDateTime createdDateTime;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

}
