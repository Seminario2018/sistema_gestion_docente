package modelo.usuario;

import org.junit.Assert;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.hash.PasswordUtil;
import modelo.persona.GestorPersona;
import modelo.persona.Persona;
import modelo.usuario.GestorUsuario;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import modelo.usuario.Permiso;
import modelo.usuario.Rol;
import modelo.usuario.Usuario;

public class UsuarioTest {

	@Test
	public void agregarPersonaUsuarioRol() {
		Persona p = new Persona("Marazzo", "Leonardo", LocalDate.of(1997, 6, 22),
				TipoDocumento.DNI, 40455634, null, null, null, EstadoPersona.ACTIVO);
		
		GestorPersona gp = new GestorPersona();
		System.out.println(gp.nuevoUsuario(p).getMensaje());
		
		Usuario u = new Usuario("leomarazzo", "leonardomarazzo", "Leonardo", new ArrayList<IRol>());
		u.setPersona(p);
		
		GestorUsuario gu = new GestorUsuario();
		System.out.println(gu.nuevoUsuario(u).getMensaje());
		
		Rol r = new Rol("su");
		Permiso per  = new Permiso(Modulo.CARGOS);
		per.setCrear(true);
		per.setEliminar(true);
		per.setModificar(true);
		per.setListar(true);
		r.agregarPermiso(per);
		
		EstadoOperacion eo = gu.agregarRol(u, r);
		System.out.println(eo.getMensaje());
		Assert.assertEquals(eo.getEstado(), EstadoOperacion.CodigoEstado.INSERT_OK);
	}
	
	@Test
	public void validarContrasena() {
		GestorUsuario gu = new GestorUsuario();
		
		Usuario user = new Usuario("leomarazzo", "leonardomarazzo", "Leonardo", new ArrayList<IRol>());
		gu.nuevoUsuario(user);
		
		Usuario u = (Usuario) gu.listarUsuario(user).get(0);
		
		String hash = u.getHash().getHash();
		String salt = u.getHash().getSalt();
		
		System.out.println(hash + " | " + salt);
		
		boolean estado1 = PasswordUtil.ValidatePass("leonardomarazzo", hash, salt);
		
		Assert.assertTrue(estado1);
	}

}
