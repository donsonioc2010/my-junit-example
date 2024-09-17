package com.example.junitexample.domain;

import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Comment("최초등록일시")
    @CreationTimestamp
    private Timestamp createdAt;

    @CreatedBy
    @Comment("최초등록자ID")
    private String createdUserId;

    @UpdateTimestamp
    @Comment("최종변경일시")
    private Timestamp updatedAt;

    @LastModifiedBy
    @Comment("최종변경자ID")
    private String updatedUserId;

}
