package modelo.division;

import java.time.LocalDate;
import modelo.docente.IDocente;

public interface IDivision {

    public IDivision clone();
    public String getCodigo();
    public void setCodigo(String codigo);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public IDocente getJefe();
    public void setJefe(IDocente jefe);
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public LocalDate getDispDesde();
    public void setDispDesde(LocalDate dispDesde);
    public LocalDate getDispHasta();
    public void setDispHasta(LocalDate dispHasta);

}