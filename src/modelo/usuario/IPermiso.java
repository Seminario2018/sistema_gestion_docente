package modelo.usuario;


public interface IPermiso {
    public IPermiso clone();
    public Modulo getModulo();
    public void setModulo(Modulo modulo);
    public boolean getCrear();
    public void setCrear(boolean crear);
    public boolean getModificar();
    public void setModificar(boolean modificar);
    public boolean getEliminar();
    public void setEliminar(boolean eliminar);
    public boolean getListar();
    public void setListar(boolean listar);
}