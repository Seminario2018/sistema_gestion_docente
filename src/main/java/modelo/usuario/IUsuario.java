package modelo.usuario;

import java.util.List;

public interface IUsuario {
    public IUsuario clone();
    public int getId();
    public void setId(int id);
    public String getUser();
    public void setUser(String user);
    public String getPass();
    public void setPass(String pass);
    public String getDescripcion();
    public void setDescripcion(String descripcion);

    public List<IRol> getGrupos();
    public void agregarGrupo(IRol grupo);
    public void quitarGrupo(IRol grupo);
}