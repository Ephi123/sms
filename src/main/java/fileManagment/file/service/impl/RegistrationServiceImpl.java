package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.repository.RegistrationRepo;
import fileManagment.file.requestDto.RegistrationRequest;
import fileManagment.file.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static fileManagment.file.util.RequestUtil.handleErrorResponse;


@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
     private final RegistrationRepo registrationRepo;
     private final HttpServletRequest request;
     private final HttpServletResponse response;
    @Override
    public void updateRegistrationInfo(RegistrationRequest registrationRequest) {
          var registration = registrationRepo.findById("reg").orElseThrow(() -> new ApiException("grade is not found"));
         if(registrationRequest.getFee() == null && registrationRequest.getStartDate() == null && registrationRequest.getEndDate() == null){

             handleErrorResponse(request, response, new ApiException("nothing value to update"), HttpStatus.BAD_REQUEST);
             throw new ApiException("nothing value to update ");
         }

         if (registrationRequest.getFee() != null)
                 registration.setRegistrationFee(registrationRequest.getFee());
          if(registrationRequest.getEndDate() != null)
                 registration.setRegistrationEndDate(registrationRequest.getEndDate());
          if(registrationRequest.getStartDate() != null)
              registration.setRegistrationStartDate(registrationRequest.getStartDate());

          registrationRepo.save(registration);

    }

}
