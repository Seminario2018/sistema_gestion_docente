package modelo.usuario;

import java.util.ArrayList;
import java.util.List;

import modelo.persona.Persona;

public class Usuario implements IUsuario {
	private int id;
	private Persona persona;
	private String user;
	private String pass;
	private String descripcion;

	private List<IRol> roles;

	public Usuario(int id, String user, String pass, String descripcion,
	        List<IRol> roles) {

	    this.id = id;
	    this.user = user;
	    this.pass = pass;
	    this.descripcion = descripcion;
	    this.roles = new ArrayList<IRol>(roles);
	}

    @Override
    public IUsuario clone() {
        return (IUsuario) new Usuario(
            this.id,
            this.user,
            this.pass,
            this.descripcion,
            this.roles
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
    public String getUser() {
        return this.user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPass() {
        return this.pass;
    }

    @Override
    public void setPass(String pass) {
        this.pass = pass;
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
    public List<IRol> getGrupos() {
        return this.roles;
    }

    @Override
    public void agregarGrupo(IRol grupo) {
        this.roles.add(grupo);
    }

    @Override
    public void quitarGrupo(IRol grupo) {
        this.roles.remove(grupo);
    }

}