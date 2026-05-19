package com.project1.sms.repository;

import com.project1.sms.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByPhone(String string);
    Boolean existsByUserName(String userName);

}
