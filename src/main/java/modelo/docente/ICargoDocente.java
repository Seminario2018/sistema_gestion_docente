package modelo.docente;

import java.time.LocalDate;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;

public interface ICargoDocente {
    public ICargoDocente clone();
    public int getId();
    public void setId(int id);
    public IArea getArea();
    public void setArea(IArea area);
    public ICargo getCargo();
    public void setCargo(ICargo cargo);
    public TipoCargo getTipoCargo();
    public void setTipoCargo(TipoCargo tipoCargo);
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public LocalDate getDispDesde();
    public void setDispDesde(LocalDate dispDesde);
    public LocalDate getDispHasta();
    public void setDispHasta(LocalDate dispHasta);
    public float getUltimoCosto();
    public void setUltimoCosto(float ultimoCosto);
    public LocalDate getFechaUltCost();
    public void setFechaUltCost(LocalDate fechaUltCost);
    public String getResolucion();
    public void setResolucion(String resolucion);
    public LocalDate getResDesde();
    public void setResDesde(LocalDate resDesde);
    public LocalDate getResHasta();
    public void setResHasta(LocalDate resHasta);
    public EstadoCargo getEstado();
    public void setEstado(EstadoCargo estado);
}