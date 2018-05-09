package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public interface IArea {
    public IArea clone();
    public String getCodigo();
    public void setCodigo(String codigo);
    public String getDescripcion();
    public void setDescripcion(String descripcion);
    public IDivision getDivision();
    public void setDivision(IDivision division);
    public IDocente getDocenteResponsable();
    public void setDocenteResponsable(IDocente responsable);
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public LocalDate getDispDesde();
    public void setDispDesde(LocalDate dispDesde);
    public LocalDate getDispHasta();
    public void setDispHasta(LocalDate dispHasta);
    public IArea getAreaDe();
    public void setAreaDe(IArea subAreaDe);
}