package fileManagment.file.event.eventListner;

import fileManagment.file.event.UserEvent;
import fileManagment.file.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final EmailService emailService;
    @EventListener
    public void onUserEvent(UserEvent userEvent){

        switch (userEvent.getType()){
            case REGISTER -> emailService.sendNewAccountEmail(userEvent.getUser().getFirstName(), userEvent.getUser().getEmail(),(String) userEvent.getData().get("key"),userEvent.getPassword());
            case RESET_PASSWORD -> emailService.sendPasswordRestore(userEvent.getUser().getFirstName(), userEvent.getUser().getEmail(),(String) userEvent.getData().get("key"));
            default -> {}
        }
    }

    }



