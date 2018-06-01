package mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	public static List<Map<String, String>> colaMails = new ArrayList<Map<String, String>>();

    /* (non-Javadoc)
     * @see mail.IMail#enviarEmail(mail.IDocente, mail.ICargo)
     */
    @Override
    public boolean enviarEmail(String mailDesde, String mailHasta, String asunto,
                            String mensaje, String contrasena) {

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
