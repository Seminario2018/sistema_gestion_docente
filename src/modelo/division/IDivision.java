package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public interface IDivision {

    public IDivision clone();
    public int getCodigo();
    public void setCodigo(int codigo);
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