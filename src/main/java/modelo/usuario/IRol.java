package modelo.usuario;

import java.util.List;

public interface IRol {
    public IRol clone();
    public String getNombre();
    public void setNombre(String nombre);

    public List<IPermiso> getPermisos();
    public void agregarPermiso(IPermiso permiso);
    public void quitarPermiso(IPermiso permiso);
}