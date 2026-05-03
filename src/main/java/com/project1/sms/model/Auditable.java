package com.project1.sms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt, updatedAt"}, allowGetters = true)
public abstract class  Auditable {

    @Id
    @SequenceGenerator(name = "primary_key_sequence", sequenceName ="primary_key_sequence",allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_sequence" )
    private Long id;
    @NotNull
    private Long createdBy;

    @NotNull
    private Long updatedBy;@NotNull
    @CreatedDate
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @NotNull
    @CreatedDate
    @Column(name = "update_at",nullable = false)
    private LocalDateTime updateAt;

    @PrePersist
    public void beforePersist(){
        var userId = 0L; //RequestContext.getUserId();
//        if(userId == null)
//              throw new ApiException("can not Persist entity without userId in RequestContext for this thread");
        setCreatedAt(now());
        setUpdateAt(now());
        setCreatedBy(userId);
        setUpdatedBy(userId);
    }

    @PreUpdate
    public void beforeUpdate(){
        var userId = 0L; //RequestContext.getUserId();
//        if(userId == null)
//            throw new ApiException("can not Update entity without userId in RequestContext for this thread");
        setUpdateAt(now());
        setUpdatedBy(userId);
    }


}
