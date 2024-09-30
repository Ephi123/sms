package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaymentCollection extends Auditable{
    private Integer academicYear;
    private Double  payment;
    private Boolean isChecked;
    @Column(name = "transaction")
    private String transactionId =  new AlternativeJdkIdGenerator().generateId().toString();;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "payments_students", joinColumns = @JoinColumn(name = "payment_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    private List<UserEntity> students;
    public void addToStudent(UserEntity student){
        students = new ArrayList<>();
        students.add(student);
    }

}
