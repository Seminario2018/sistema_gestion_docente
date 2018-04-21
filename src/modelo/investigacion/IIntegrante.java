package modelo.investigacion;

import modelo.docente.IDocente;
import modelo.docente.IPlanta;

public interface IIntegrante {
    public IIntegrante clone();
    public String getApellidoNombre();
    public void setApellidoNombre(IDocente docente);
    public String getCargo();
    public void setCargo(IPlanta planta);
    public String getInstitucion();
    public int getHorasSemanales();
    public void setHorasSemanales(int horasSemanales);
}