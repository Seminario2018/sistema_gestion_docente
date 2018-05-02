package mail;

import java.util.Scanner;

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
	public void enviarMailGenerico() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Desde:");
		String mailDesde = sc.nextLine();
		
		System.out.println("Contraseña:");
		String contrasena = sc.nextLine();
		
		System.out.println("Hasta:");
		String mailHasta = sc.nextLine();
		
		System.out.println("Asunto:");
		String asunto = sc.nextLine();
		
		System.out.println("Mensaje:");
		String mensaje = "";
		String chunk = sc.nextLine();
		while (!chunk.equals("")) {
			mensaje += chunk + "\r\n";
			chunk = sc.nextLine();
		}
		
		System.out.println("Enviando mensaje...");
		
		IMail m = new Mail();
		
		m.enviarEmail(mailDesde, mailHasta, asunto, mensaje, contrasena);
		
		System.out.println("Enviado exitosamente");
		
		sc.close();
	}
}
