package modelo.investigacion;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;

public interface IProyecto {
    public IProyecto clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getResumen();
    public void setResumen(String resumen);
    public LocalDate getFechaPresentacion();
    public void setFechaPresentacion(LocalDate fechaPresentacion);
    public LocalDate getFechaAprobacion();
    public void setFechaAprobacion(LocalDate fechaAprobacion);
    public String getDescripcion();
    public void setDescripcion(String disposicion);
    public IDocente getDirector();
    public void setDirector(IDocente director);
    public IDocente getCodirector();
    public void setCodirector(IDocente codirector);
    public LocalDate getFechaInicio();
    public void setFechaInicio(LocalDate fechaInicio);
    public LocalDate getFechaFin();
    public void setFechaFin(LocalDate fechaFin);
    public EstadoProyecto getEstado();
    public void setEstado(EstadoProyecto estado);

    public List<IIntegrante> getIntegrantes();
    public void agregarIntegrante(IIntegrante integrante);
    public void quitarIntegrante(IIntegrante integrante);

    public List<ISubsidio> getSubsidios();
    public void agregarSubsidio(ISubsidio subsidio);
    public void quitarSubsidio(ISubsidio subsidio);

    public List<IProrroga> getProrrogas();
    public void agregarProrroga(IProrroga prorroga);
    public void quitarProrroga(IProrroga prorroga);
}