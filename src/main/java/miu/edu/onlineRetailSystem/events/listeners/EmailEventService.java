package miu.edu.onlineRetailSystem.events.listeners;

import miu.edu.onlineRetailSystem.events.EmailEvent;

public interface EmailEventService {
    void sendEmail(EmailEvent emailEvent);
}
