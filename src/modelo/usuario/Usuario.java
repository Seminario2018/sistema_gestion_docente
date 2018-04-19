package modelo.usuario;

import java.util.ArrayList;

public class Usuario implements IUsuario {
	private int id;
	private String user;
	private String pass;
	private String descripcion;

	private ArrayList<IGrupo> grupos;

	public Usuario(int id, String user, String pass, String descripcion,
	        ArrayList<IGrupo> grupos) {

	    this.id = id;
	    this.user = user;
	    this.pass = pass;
	    this.descripcion = descripcion;
	    this.grupos = grupos;
	}

    @Override
    public IUsuario clone() {
        return (IUsuario) new Usuario(
            this.id,
            this.user,
            this.pass,
            this.descripcion,
            this.grupos
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
    public ArrayList<IGrupo> getGrupos() {
        return this.grupos;
    }

    @Override
    public void agregarGrupo(IGrupo grupo) {
        this.grupos.add(grupo);
    }

    @Override
    public void quitarGrupo(IGrupo grupo) {
        this.grupos.remove(grupo);
    }

}