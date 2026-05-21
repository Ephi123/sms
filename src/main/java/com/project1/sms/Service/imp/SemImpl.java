package com.project1.sms.Service.imp;

import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.CurrentSem;
import com.project1.sms.repository.CurrentSemRepo;

import com.project1.sms.Service.SemsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemImpl implements SemsterService {
    private CurrentSemRepo semRepo;
    @Override
    public void updateSemster(Long id,int sem) {
        CurrentSem sem1 =semRepo.findById(id).orElseThrow(() -> new ApiException("sem is not found"));
        sem1.setCurrentSem(sem);
        semRepo.save(sem1);

    }
}
