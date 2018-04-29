package modelo.investigacion;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;

public interface IPrograma {
    public IPrograma clone();
    public int getId();
    public void setId(int id);
    public String getNombre();
    public void setNombre(String nombre);
    public String getDisposicion();
    public void setDisposicion(String disposicion);
    public IDocente getDirector();
    public void setDirector(IDocente director);
    public IDocente getCodirector();
    public void setCodirector(IDocente codirector);
    public LocalDate getFechaInicio();
    public void setFechaInicio(LocalDate fechaInicio);
    public LocalDate getFechaFin();
    public void setFechaFin(LocalDate fechaFin);
    public EstadoPrograma getEstado();
    public void setEstado(EstadoPrograma estado);

    public List<IProyecto> getProyectos();
    public void agregarProyecto(IProyecto proyecto);
    public void quitarProyecto(IProyecto proyecto);

}