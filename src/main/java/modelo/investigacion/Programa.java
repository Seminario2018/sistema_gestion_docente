package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;

public class Programa implements IPrograma {
	private int id;
	private String nombre;
	private String disposicion;
	private IDocente director;
	private IDocente codirector;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private EstadoPrograma estado;

	private List<IProyecto> proyectos;

    public Programa(){
        
    }

	public Programa(int id, String nombre, String disposicion,
	        IDocente director, IDocente codirector, LocalDate fechaInicio,
	        LocalDate fechaFin, EstadoPrograma estado,
	        List<IProyecto> proyectos) {
	    this.id = id;
	    this.nombre = nombre;
	    this.disposicion = disposicion;
	    this.director = director;
	    this.codirector = codirector;
	    this.fechaInicio = fechaInicio;
	    this.fechaFin = fechaFin;
	    this.estado = estado;
	    this.proyectos = new ArrayList<IProyecto>(proyectos);
	}

    @Override
    public IPrograma clone() {
        return (IPrograma) new Programa(
            this.id,
            this.nombre,
            this.disposicion,
            this.director,
            this.codirector,
            this.fechaInicio,
            this.fechaFin,
            this.estado,
            this.proyectos
            );
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getDisposicion() {
        return this.disposicion;
    }

    @Override
    public void setDisposicion(String disposicion) {
        this.disposicion = disposicion;
    }

    @Override
    public IDocente getDirector() {
        return this.director;
    }

    @Override
    public void setDirector(IDocente director) {
        this.director = director;
    }

    @Override
    public IDocente getCodirector() {
        return this.codirector;
    }

    @Override
    public void setCodirector(IDocente codirector) {
        this.codirector = codirector;
    }

    @Override
    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    @Override
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    @Override
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public EstadoPrograma getEstado() {
        return this.estado;
    }

    @Override
    public void setEstado(EstadoPrograma estado) {
        this.estado = estado;
    }

    @Override
    public List<IProyecto> getProyectos() {
        return this.proyectos;
    }

    @Override
    public void agregarProyecto(IProyecto proyecto) {
        this.proyectos.add(proyecto);
    }

    @Override
    public void quitarProyecto(IProyecto proyecto) {
        this.proyectos.remove(proyecto);
    }

}