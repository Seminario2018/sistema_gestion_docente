package modelo.cargo;


public interface ICargo {
    public ICargo clone();
    public int getCodigo();
    public void setCodigo(int codigo);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public int getCargaHoraria();
    public void setCargaHoraria(int cargaHoraria);
}