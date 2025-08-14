package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.RequestContext;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt,updateAt"},allowGetters = true)
public abstract class Auditable {
    @Id
    @SequenceGenerator(name = "primary_key_seq",sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "primary_key_seq")
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;

    @NotNull
    private Long updatedBy;
    @NotNull
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
