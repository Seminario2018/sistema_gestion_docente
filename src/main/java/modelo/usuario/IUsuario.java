package modelo.usuario;

import java.util.List;
import modelo.auxiliares.hash.HashSalt;
import modelo.persona.IPersona;

public interface IUsuario {
    public IUsuario clone();
    public String getUser();
    public void setUser(String user);
    public HashSalt getHash();
    public void setPass(String pass);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
	public IPersona getPersona();
	public void setPersona(IPersona persona);

	public List<IRol> getRoles();
    public void setGrupos(List<IRol> grupos);
    public void agregarRol(IRol grupo);
    public void quitarGrupo(IRol grupo);
}