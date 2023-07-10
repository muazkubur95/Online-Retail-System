package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.events.EmailEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EmailEventServiceImpl implements EmailEventService {
    @Override
    @EventListener
    public void sendEmail(EmailEvent emailEvent) {
        System.out.println("Email sent to: " + emailEvent.getTo() + "\n Message: " +
                emailEvent.getMessage());
    }
}
