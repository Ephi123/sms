package fileManagment.file.service;

import fileManagment.file.entity.EnrolEntity;

import java.util.List;
import java.util.Map;

public interface EnrolService {
    EnrolEntity register(String firstName,
                         String lastName,
                         String email,
                         String authority,
                         Integer age,
                         String address,
                         String gender,
                         String userId, Integer grade, Integer field);

    List<Map<?,?>> getRegistrationFee();
}