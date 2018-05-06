package mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail implements IMail {

    private String mailDesde = "semint2018@gmail.com";
    private String contrasena = "semintunlu";

    @Override
    public boolean enviarEmail(String mailHasta, String asunto, String mensaje) {

        // Propiedades del sistema:
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Obtener el objeto de sesión por defecto:
        Session session = Session.getInstance(propiedades,
                                              new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailDesde, contrasena);
            }
        });

        try {
           // Crear un objeto MimeMessage por defecto:
           Message mensajeMime = new MimeMessage(session);

           // Emisor:
           mensajeMime.setFrom(new InternetAddress(mailDesde));

           // Receptor:
           mensajeMime.setRecipients(Message.RecipientType.TO,
                                    InternetAddress.parse(mailHasta));

           // Asunto:
           mensajeMime.setSubject(asunto);

           // Mensaje:
           mensajeMime.setText(mensaje);

           // Enviar mensaje:
           Transport.send(mensajeMime);

           return true;

        } catch (MessagingException e) {
           e.printStackTrace();

           return false;
        }
    }

}
