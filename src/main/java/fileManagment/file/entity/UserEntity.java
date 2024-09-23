package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity extends Auditable {
    @Column(updatable = false,unique = true,nullable = false)
    private String userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String  gender;
    private String  address;
    @Column(unique = true,nullable = false)
    private String email;
    private String phone;
    private Integer loginAttempt;
    private LocalDateTime lastLogin;
    private String bio;
    private String imageUrl;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enable;
    private boolean mfa;
    @JsonIgnore
    private String qrCodeSecret;
    @Column(columnDefinition = "text")
    private String qrCodeImage;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")

    )
    private List<RoleEntity> roles;


    public void addToRole(RoleEntity role){
        roles = new ArrayList<>();
        roles.add(role);
    }
}
