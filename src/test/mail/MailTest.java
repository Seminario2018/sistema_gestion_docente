package test.mail;

import mail.IMail;
import mail.Mail;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */
public class MailTest {
	public static void main(String[] args) {
		String mailDesde = "semint2018@gmail.com";
		String contrasena = "semintunlu";
		String mailHasta = "tomasjuran96@gmail.com";
		String asunto = "Prueba 1";
		String mensaje = "Prueba de mail #1";
		
		IMail m = new Mail();
		
		m.enviarEmail(mailDesde, mailHasta, asunto, mensaje, contrasena);
	}
}
