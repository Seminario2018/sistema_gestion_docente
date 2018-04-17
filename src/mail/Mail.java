package mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.cargo.ICargo;
import modelo.docente.IDocente;

public class Mail implements IMail {

    /* (non-Javadoc)
     * @see mail.IMail#enviarEmail(mail.IDocente, mail.ICargo)
     */
    @Override
    public void enviarEmail(IDocente docente, ICargo cargo) {
        
        // Datos del email:
        Map<String, String> datosEmail = new HashMap<String, String>();
        // TODO Definir datos del email
        datosEmail.put("to", docente.getMailLaboral());
        datosEmail.put("from", "Admin@localhost");
        datosEmail.put("host", "localhost");
        datosEmail.put("subject", "Asunto de Prueba 004");
        datosEmail.put("message", "Mensaje de prueba.");

        // Propiedades del sistema:
        Properties propiedades = System.getProperties();

        // Configurar servidor de email:
        propiedades.setProperty("smtp.gmail.com", datosEmail.get("host"));

        // Obtener el objeto de sesión por defecto:
        Session session = Session.getDefaultInstance(propiedades);

        try {
           // Crear un objeto MimeMessage por defecto:
           MimeMessage mensajeMime = new MimeMessage(session);

           mensajeMime.setFrom(new InternetAddress(datosEmail.get("from")));
           mensajeMime.addRecipient(Message.RecipientType.TO, new InternetAddress(datosEmail.get("to")));

           // Asunto:
           mensajeMime.setSubject(datosEmail.get("subject"));

           // Mensaje:
           mensajeMime.setText(datosEmail.get("message"));

           // Enviar mensaje:
           Transport.send(mensajeMime);
           
        } catch (MessagingException mex) {
           mex.printStackTrace();
        }
    }

}
