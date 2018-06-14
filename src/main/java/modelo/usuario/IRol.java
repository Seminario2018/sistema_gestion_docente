package modelo.usuario;

import java.util.List;

public interface IRol {
    public IRol clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getDescripcion();
    public void setDescripcion(String descripcion);

    public List<IPermiso> getPermisos();
    public void agregarPermiso(IPermiso permiso);
    public void quitarPermiso(IPermiso permiso);
}