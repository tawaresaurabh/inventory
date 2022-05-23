package fi.plasmonics.inventory.services;

public interface EmailService {


    void sendSimpleMessage(
                           String subject,
                           String text,
                           String[] to);



}
