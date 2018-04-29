package modelo.usuario;

import java.util.ArrayList;
import java.util.List;

public class Rol implements IRol {
	private int id;
	private String nombre;

	private List<IPermiso> permisos;

	public Rol(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.permisos = new ArrayList<IPermiso>();
		for (Modulo m: Modulo.values()) {
			Permiso p = new Permiso(m);
			permisos.add(p);
		}
	}

	public Rol(int id, String nombre, List<IPermiso> permisos) {
	    this.id = id;
        this.nombre = nombre;
        this.permisos = new ArrayList<IPermiso>(permisos);
	}

    @Override
    public IRol clone() {
        return (IRol) new Rol(
            this.id,
            this.nombre,
            this.permisos
            );
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public List<IPermiso> getPermisos() {
        return this.permisos;
    }

    @Override
    public void agregarPermiso(IPermiso permiso) {
        this.permisos.add(permiso);
    }

    @Override
    public void quitarPermiso(IPermiso permiso) {
        this.permisos.remove(permiso);
    }
}