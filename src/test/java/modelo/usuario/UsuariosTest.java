package modelo.usuario;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import modelo.auxiliares.TipoDocumento;
import modelo.persona.Persona;

public class UsuariosTest {

	@Test
	public void usuariosTest() {
		Persona per =  new Persona();
		per.setTipoDocumento(new TipoDocumento(0, "DNI"));
		per.setNroDocumento(40455634);
		
		
		GestorRol gr = new GestorRol();
		GestorUsuario gu = new GestorUsuario();
		
		Rol r = new Rol(1, "admin");
		for (IPermiso p : r.getPermisos()) {
			p.setCrear(true);
			p.setEliminar(true);
			p.setListar(true);
			p.setModificar(true);
		}
		System.out.println(gr.nuevoGrupo(r));
		
		List<IRol> roles = new ArrayList<IRol>();
		roles.add(r);
		Usuario u = new Usuario("admin", "admin", "SuperUsuario", roles);
		
		u.setPersona(per);
		System.out.println(gu.nuevoUsuario(u));
		
		
		r = new Rol(2, "usuario");
		for (IPermiso p : r.getPermisos()) {
			if (p.getModulo() != Modulo.GRUPOS && p.getModulo() != Modulo.USUARIOS) {
				p.setCrear(true);
				p.setEliminar(true);
				p.setListar(true);
				p.setModificar(true);
			}			
		}
		System.out.println(gr.nuevoGrupo(r));
		
		roles = new ArrayList<IRol>();
		roles.add(r);
		u = new Usuario("usuario", "usuario", "Usuario standard", roles);
		
		u.setPersona(per);
		System.out.println(gu.nuevoUsuario(u));
		
		
		r = new Rol(3, "invitado");
		for (IPermiso p : r.getPermisos()) {
			if (p.getModulo() != Modulo.GRUPOS && p.getModulo() != Modulo.USUARIOS) {
				p.setListar(true);
			}			
		}
		System.out.println(gr.nuevoGrupo(r));
		
		
		roles = new ArrayList<IRol>();
		roles.add(r);
		
		u = new Usuario("invitado", "invitado", "Usuario invitado", roles);
		u.setPersona(per);
		
		
		System.out.println(gu.nuevoUsuario(u));
		
		
	}

}
