package modelo.investigacion;

public interface IIntegrante {
    public IIntegrante clone();
    public String getApellidoNombre();
    public void setApellidoNombre(String apellidoNombre);
    public String getCargo();
    public void setCargo(String cargo);
    public String getInstitucion();
    public int getHorasSemanales();
    public void setHorasSemanales(int horasSemanales);
}