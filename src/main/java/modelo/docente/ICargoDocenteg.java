package modelo.docente;

import java.time.LocalDate;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;

public interface ICargoDocenteg {
	
	public int getId();
	public IArea getArea2();
	public ICargo getCargo2();
	public TipoCargo getTipoCargo2();
	public String getDisposicion();
	public LocalDate getDispDesde();
	public LocalDate getDispHasta();
	public float getUltimoCosto();
	public LocalDate getFechaUltCost();
	public String getResolucion();
	public LocalDate getResDesde();
	public LocalDate getResHasta();
	public EstadoCargo getEstado2();
	
	
	
}
