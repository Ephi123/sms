package fileManagment.file.service;

import fileManagment.file.entity.EnrolEntity;

public interface EnrolService {
    EnrolEntity register(String firstName,
                         String lastName,
                         String email,
                         String authority,
                         Integer age,
                         String address,
                         String gender,
                         String userId, Integer grade, Integer field);
}
