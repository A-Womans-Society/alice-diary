package com.alice.project.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 테이블로 매핑하지 않고, 자식 클래스(엔티티)에게 매핑정보를 상속하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class) // JPA에게 해당 Entity는 Auditing기능을 사용한다는 것을 알리는 어노테이션
public abstract class TimeEntity {
    @CreatedDate // 속성을 추가하지 않으면 수정 시, 해당 값은 null이 되어버림
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate // Entity가 수정될때 수정일자를 주입
    private LocalDateTime modifiedDate;
}