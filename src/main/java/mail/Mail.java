package mail;

import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.w3c.dom.Document;
import utilidades.Utilidades;

public class Mail implements IMail {

    private String usuario = null;
    private String contraseña = null;
    private String servidor = null;
    private String port = null;
    private String tls = null;
    private String pie = null;

    public Mail() {
        // Leo los parámetros desde el XML:
        Document document = Utilidades.leerXML(new File("mail.xml"));

        this.usuario = document.getElementsByTagName("usuario").item(0).getTextContent();
        this.contraseña = document.getElementsByTagName("contraseña").item(0).getTextContent();
        this.servidor = document.getElementsByTagName("servidor").item(0).getTextContent();
        this.port = document.getElementsByTagName("puerto").item(0).getTextContent();
        this.tls = document.getElementsByTagName("tls").item(0).getTextContent();

        // Obtener el pie del mensaje de la plantilla:
        Document plantillaXML = Utilidades.leerXML(new File("Plantilla.xml"));
        this.pie = plantillaXML.getElementsByTagName("pie").item(0).getTextContent();
    }

    /* (non-Javadoc)
     * @see mail.IMail#enviarEmail(mail.IDocente, mail.ICargo)
     */
    @Override
    public boolean enviarEmail(
        String destino,
        String asunto,
        String mensaje) {

        // Propiedades del sistema:
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", this.servidor);
        propiedades.put("mail.smtp.port", this.port);
        propiedades.put("mail.smtp.starttls.enable", this.tls);
        propiedades.put("mail.smtp.ssl.trust", this.servidor);

        // Obtener el objeto de sesión por defecto:
        Session session = Session.getInstance(propiedades, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        });

        try {
           // Crear un objeto MimeMessage por defecto:
           MimeMessage mensajeMime = new MimeMessage(session);

           // Emisor:
           mensajeMime.setFrom(new InternetAddress(this.usuario));

           // Receptor:
           mensajeMime.setRecipients(Message.RecipientType.TO,
                                    InternetAddress.parse(destino));

           // Asunto:
           mensajeMime.setSubject(asunto);

           // Mensaje:
           mensajeMime.setText(mensaje + this.pie, "utf-8", "html");

           // Enviar mensaje:
           Transport.send(mensajeMime);

           return true;

        } catch (MessagingException e) {
           e.printStackTrace();
           return false;
        }
    }

    @Override
    public String getUsuario() {
        return this.usuario;
    }

}
