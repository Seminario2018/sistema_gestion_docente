package personaUsuarios;

import org.junit.Assert;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;
import modelo.auxiliares.hash.PasswordUtil;
import modelo.persona.GestorPersona;
import modelo.persona.Persona;
import modelo.usuario.GestorUsuario;
import modelo.usuario.IRol;
import modelo.usuario.Usuario;

public class PersonaUsuarios {

	@Test
	public void test() {
		Persona p = new Persona("Marazzo", "Leonardo", LocalDate.of(1997, 6, 22),
				TipoDocumento.DNI, 40455634, null, null, null, EstadoPersona.ACTIVO);
		GestorPersona gp = new GestorPersona();
		System.out.println(gp.nuevoUsuario(p).getMensaje());
		
		Usuario u = new Usuario("leomarazzo", "leonardomarazzo", "Leonardo", new ArrayList<IRol>());
		u.setPersona(p);
		
		System.out.println(u.getHash().getHash());
		
		GestorUsuario gu = new GestorUsuario();
		System.out.println(gu.nuevoUsuario(u).getMensaje());
		
	}
	
	@Test
	public void test2() {
		GestorUsuario gu = new GestorUsuario();
		Usuario user = new Usuario("leomarazzo", "leonardomarazzo", "Leonardo", new ArrayList<IRol>());
		Usuario u = (Usuario) gu.listarUsuario(user).get(0);
		
		
		String hash = u.getHash().getHash();
		String salt = u.getHash().getSalt();
		
		System.out.println(hash + " | " + salt);
		
		boolean estado1 = PasswordUtil.ValidatePass("leonardomarazzo", hash, salt);
		
		
		Assert.assertTrue(estado1);
		
	}

}
