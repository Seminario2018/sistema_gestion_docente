package modelo.usuario;

import java.util.ArrayList;
import java.util.List;
import modelo.auxiliares.hash.HashSalt;
import modelo.auxiliares.hash.PasswordUtil;
import modelo.persona.IPersona;

public class Usuario implements IUsuario{
	private IPersona persona;
	private String user = "";
	private HashSalt hash;
	private String descripcion;

	private List<IRol> roles;

	public Usuario() {
	    this.persona = null;
	    this.user = "";
	    this.hash = null;
	    this.descripcion = "";
	    this.roles = new ArrayList<IRol>();
	}

	public Usuario(String user, HashSalt hash, String descripcion,
	        List<IRol> roles) {

	    this.user = user;
	    this.hash = hash;
	    this.descripcion = descripcion;
	    this.roles = roles;
	}

	public Usuario(String user, String pass, String descripcion,
	        List<IRol> roles) {

	    this.user = user;
	    this.setPass(pass);
	    this.descripcion = descripcion;
	    this.roles = roles;
	}

    @Override
    public IUsuario clone() {
        return (IUsuario) new Usuario(
            this.user,
            this.hash,
            this.descripcion,
            this.roles
         );
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
    public HashSalt getHash() {
        return this.hash;
    }

    @Override
    public void setPass(String pass) {
        try {
			this.hash = PasswordUtil.getHash(pass);
		} catch (Exception e) {
			this.hash = new HashSalt();
		}
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

	@Override
    public IPersona getPersona() {
		return persona;
	}

	@Override
    public void setPersona(IPersona persona) {
		this.persona = persona;
	}


	@Override
    public void setGrupos(List<IRol> grupos) {
        this.roles = grupos;
    }

}