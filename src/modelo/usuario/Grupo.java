package modelo.usuario;

import java.util.ArrayList;

public class Grupo implements IGrupo {
	private int id;
	private String nombre;
	
	private ArrayList<IPermiso> permisos;
	
	public Grupo(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre; 
		this.permisos = new ArrayList<IPermiso>();
		for (Modulo m: Modulo.values()) {
			Permiso p = new Permiso(m);
			permisos.add(p);
		}
	}
}