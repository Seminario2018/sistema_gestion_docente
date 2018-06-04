package modelo.investigacion;

import java.time.LocalDate;

import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;

public interface IProyectog {

	public int getId();
	public String getNombre();
	public String getResumen();
	public LocalDate getFechaPresentacion();
	public LocalDate getFechaAprobacion();
	public String getDescripcion();
	public IDocente getDirector2();
	public IDocente getCodirector2();
	public LocalDate getFechaInicio();
	public LocalDate getFechaFin();
	public EstadoProyecto getEstado2();
}
