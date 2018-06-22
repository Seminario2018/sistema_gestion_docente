package modelo.usuario;

import java.util.ArrayList;
import java.util.List;

public class Rol implements IRol {

	private int id;
	private String nombre;
	private String descripcion;

	private List<IPermiso> permisos;

	public Rol() {
	    this.id = -1;
	    this.nombre = "";
	    this.descripcion = "";
	    this.permisos = new ArrayList<IPermiso>();
	    for (Modulo m : Modulo.values()) {
	    	IPermiso p = new Permiso(m);
	    	this.permisos.add(p);
	    }
	}

	public Rol(int id) {
	    this.id = id;
	    this.nombre = "";
	    this.descripcion = "";
	    this.permisos = new ArrayList<IPermiso>();
	    for (Modulo m : Modulo.values()) {
	    	IPermiso p = new Permiso(m);
	    	this.permisos.add(p);
	    }
	}

	public Rol(int id, String nombre) {
	    this.id = id;
		this.nombre = nombre;
	    this.descripcion = "";
	    this.permisos = new ArrayList<IPermiso>();
	    for (Modulo m : Modulo.values()) {
	    	IPermiso p = new Permiso(m);
	    	this.permisos.add(p);
	    }
	}

	public Rol(int id, String nombre, List<IPermiso> permisos) {
	    this.id = id;
        this.nombre = nombre;
        this.descripcion = "";
        this.permisos = new ArrayList<IPermiso>(permisos);
	}
	
	public Rol(int id, String nombre, String descripcion, List<IPermiso> permisos) {
	    this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = new ArrayList<IPermiso>(permisos);
	}

    @Override
    public IRol clone() {
        return new Rol(
            this.id,
            this.nombre,
            this.descripcion,
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
    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (objeto == this) {
            return true;
        }
        if (!(objeto instanceof Rol)) {
            return false;
        }

        Rol rol = (Rol) objeto;
        return this.id == rol.id;
    }
}