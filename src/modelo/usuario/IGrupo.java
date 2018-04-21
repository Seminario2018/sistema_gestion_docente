package modelo.usuario;

import java.util.List;

public interface IGrupo {
    public IGrupo clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);

    public List<IPermiso> getPermisos();
    public void agregarPermiso(IPermiso permiso);
    public void quitarPermiso(IPermiso permiso);
}