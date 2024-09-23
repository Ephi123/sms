package fileManagment.file.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import fileManagment.file.enumeration.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RoleEntity extends Auditable  {
    private String name;
    private Authority authority;
}
