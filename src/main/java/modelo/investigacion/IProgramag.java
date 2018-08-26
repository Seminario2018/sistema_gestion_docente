package modelo.investigacion;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;

public interface IProgramag {
	
	public int getId();
    public String getNombre();
    public String getDisposicion();
    public IDocente getDirector2();
    public IDocente getCodirector2();
    public LocalDate getFechaInicio();
    public LocalDate getFechaFin();
    public EstadoPrograma getEstado2();
	public void setId(int i);
	public List<IProyecto> getProyectos2();

}
