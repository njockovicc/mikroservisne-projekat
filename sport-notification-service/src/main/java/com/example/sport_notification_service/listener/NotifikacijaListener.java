package com.example.sport_notification_service.listener;

import com.example.sport_notification_service.dto.SlanjeNotifikacijeDto;
import com.example.sport_notification_service.listener.helper.MessageHelper;
import com.example.sport_notification_service.service.NotifikacijaService;
import javax.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;


@Component
public class NotifikacijaListener {
    private MessageHelper messageHelper;
    private NotifikacijaService notifikacijaService;

    public NotifikacijaListener(MessageHelper messageHelper, NotifikacijaService notifikacijaService) {
        this.messageHelper = messageHelper;
        this.notifikacijaService = notifikacijaService;
    }

    /*
     * Slusa red (queue) za slanje svih notifikacija.
     * treba da imam rezervacija, izmena i otkazivanje.
     * Sva tri događaja mogu ici na isti Queue, jer naš servis na osnovu polja "tip"
     * u DTO-u zna koji šablon da povuče iz baze.
     */
    @JmsListener(destination = "${destination.posaljiNotifikaciju}", concurrency = "5-10")
    public void onPorukaStigla(Message message) throws JMSException {
        // Koristim  MessageHelper da pretvorimo JSON iz poruke u naš DTO
        SlanjeNotifikacijeDto dto = messageHelper.getMessage(message, SlanjeNotifikacijeDto.class);

        // Pozivam  metodu u servisu koja:
        // 1. Nalazi šablon
        // 2. Menja parametre (%ime, %vreme...)
        // 3. Šalje mejl
        // 4. Čuva u arhivu
        notifikacijaService.procesuirajISalji(dto);
    }

}
