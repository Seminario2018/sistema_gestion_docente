package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail implements IMail {

    /* (non-Javadoc)
     * @see mail.IMail#enviarEmail(mail.IDocente, mail.ICargo)
     */
    @Override
    public void enviarEmail(String mailDesde, String mailHasta, String asunto,
        String mensaje) {

        // Propiedades del sistema:
        Properties propiedades = System.getProperties();

        // Configurar servidor de email:
        propiedades.setProperty("smtp.gmail.com", "localhost");

        // Obtener el objeto de sesión por defecto:
        Session session = Session.getDefaultInstance(propiedades);

        try {
           // Crear un objeto MimeMessage por defecto:
           MimeMessage mensajeMime = new MimeMessage(session);

           mensajeMime.setFrom(new InternetAddress(mailDesde));
           mensajeMime.addRecipient(Message.RecipientType.TO, new InternetAddress(mailHasta));

           // Asunto:
           mensajeMime.setSubject(asunto);

           // Mensaje:
           mensajeMime.setText(mensaje);

           // Enviar mensaje:
           Transport.send(mensajeMime);

        } catch (MessagingException mex) {
           mex.printStackTrace();
        }
    }

}
