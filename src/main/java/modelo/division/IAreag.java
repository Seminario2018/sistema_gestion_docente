package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public interface IAreag {

	public String getCodigo();
	public String getDescripcion();
	public IDivision getDivision2();
	public IDocente getDocenteResponsable2();
	public String getDisposicion();
	public LocalDate getDispDesde();
	public LocalDate getDispHasta();
	public IArea getAreaDe2();
}
