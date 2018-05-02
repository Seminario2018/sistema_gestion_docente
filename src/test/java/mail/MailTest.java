package mail;

import org.junit.Assert;
import org.junit.Test;

import mail.IMail;
import mail.Mail;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */
public class MailTest {
	
	@Test
	public void enviarMail1() {
		
		String mailDesde = "semint2018@gmail.com";
		String contrasena = "semintunlu";
		String mailHasta = "nadie";
		String asunto = "Prueba";
		String mensaje = "Prueba de mail";
		
		IMail m = new Mail();
		
		boolean enviado = m.enviarEmail(mailDesde, mailHasta, asunto, mensaje, contrasena);
		Assert.assertFalse(enviado);
	}
	
	@Test
	public void enviarMail2() {
		
		String mailDesde = "semint2018@gmail.com";
		String contrasena = "semintunlu";
		String mailHasta = "sescudero2011@gmail.com";
		String asunto = "Hola";
		String mensaje = "Holaaaaa!!!";
		
		IMail m = new Mail();
		
		boolean enviado = m.enviarEmail(mailDesde, mailHasta, asunto, mensaje, contrasena);
		Assert.assertTrue(enviado);
	}
}
