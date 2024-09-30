package fileManagment.file.domain;

import lombok.*;

@Setter
@Getter
@Builder
@Data
public class Payment {
    private String month;
    private double charge;
    private double payment;
    private double total;

}
