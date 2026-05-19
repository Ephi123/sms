package com.project1.sms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;

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
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private Long updatedBy;@NotNull

    @CreatedDate
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @NotNull
    @LastModifiedDate
    @Column(name = "update_at",nullable = false)
    private LocalDateTime updateAt;

//    @PrePersist
//    public void beforePersist(Authentication authentication){
//
//
//        var userId = 0L; //RequestContext.getUserId();
////        if(userId == null)
////              throw new ApiException("can not Persist entity without userId in RequestContext for this thread");
//        setCreatedAt(now());
//        setUpdateAt(now());
//        setCreatedBy(userId);
//        setUpdatedBy(userId);
//    }
//
//    @PreUpdate
//    public void beforeUpdate(){
//        var userId = 0L; //RequestContext.getUserId();
////        if(userId == null)
////            throw new ApiException("can not Update entity without userId in RequestContext for this thread");
//        setUpdateAt(now());
//        setUpdatedBy(userId);
//    }
//

}
