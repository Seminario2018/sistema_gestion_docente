package modelo.investigacion;

import modelo.docente.ICargoDocente;

public interface IIntegrante {
    public IIntegrante clone();
    public String getCargo();
    public void setCargo(String cargo);
    public String getInstitucion();
    public int getHorasSemanales();
    public void setHorasSemanales(int horasSemanales);
    public String getApellido();
    public String getNombre();
    public void setNombre(String nombre);
    public void setApellido(String apellido);
    public void setId(int id);
    public int getId();
    public void setInstitucion(String institucion);
    public ICargoDocente getCargoDocente();
    public void setCargoDocente(ICargoDocente cd);
}