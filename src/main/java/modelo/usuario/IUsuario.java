package modelo.usuario;

import java.util.List;
import modelo.auxiliares.hash.HashSalt;
import modelo.persona.Persona;

public interface IUsuario {
    public IUsuario clone();
    public String getUser();
    public void setUser(String user);
    public HashSalt getHash();
    public void setPass(String pass);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
	public Persona getPersona();
	public void setPersona(Persona persona);

	public List<IRol> getGrupos();
    public void setGrupos(List<IRol> grupos);
    public void agregarGrupo(IRol grupo);
    public void quitarGrupo(IRol grupo);
}