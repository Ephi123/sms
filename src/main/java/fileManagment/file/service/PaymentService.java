package fileManagment.file.service;

import java.util.Map;

public interface PaymentService {
    Map<?, ?> getTuition(String userId);

    Map<?, ?> getTuitionByNumOfMonth(String userId, int numOfMonth);

    Map<?, ?> newStudentRegistrationPayment(String UserId);

    Map<?, ?> makeStudentTuitionPyament(int monthNum, String userId);

    Map<?, ?> sinorStudentRegistration(String userId);
}