package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	public void setId(int i);
	public List<IIntegrante> getIntegrantes2();
	public List<ISubsidio> getSubsidios2();
	public void setProrrogas(ArrayList<IProrroga> arrayList);
	public List<IProrroga> getProrrogas2();
	public void setIntegrantes(ArrayList<IIntegrante> arrayList);
	public void setSubsidios(ArrayList<ISubsidio> arrayList);
	
	
}
