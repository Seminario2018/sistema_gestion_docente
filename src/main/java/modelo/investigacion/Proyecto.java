package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;

public class Proyecto implements IProyecto {
	private int id;
	private String nombre;
	private String resumen;
	private LocalDate fechaPresentacion;
	private LocalDate fechaAprobacion;
	private String descripcion;
	private IDocente director;
	private IDocente codirector;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private EstadoProyecto estado;
	private IPrograma programa;

	private List<IIntegrante> integrantes;
	private List<ISubsidio> subsidios;
	private List<IProrroga> prorrogas;

	public Proyecto(){
		
	}
	public Proyecto(int id, String nombre, String resumen, LocalDate fechaPresentacion, LocalDate fechaAprobacion,
			String descripcion, IDocente director, IDocente codirector,
			LocalDate fechaInicio, LocalDate fechaFin, EstadoProyecto estado, List<IIntegrante> integrantes,
			List<ISubsidio> subsidios, List<IProrroga> prorrogas, IPrograma programa) {
		super();
		this.programa=programa;
		this.id = id;
		this.nombre = nombre;
		this.resumen = resumen;
		this.fechaPresentacion = fechaPresentacion;
		this.fechaAprobacion = fechaAprobacion;
		this.descripcion = descripcion;
		this.director = director;
		this.codirector = codirector;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.integrantes = new ArrayList<IIntegrante>(integrantes);
		this.subsidios = new ArrayList<ISubsidio>(subsidios);
		this.prorrogas = new ArrayList<IProrroga>(prorrogas);
	}

	@Override
	public IProyecto clone() {
		return (IProyecto) new Proyecto(this.id, this.nombre, this.resumen, this.fechaPresentacion,
				this.fechaAprobacion, this.descripcion, this.director, this.codirector, this.fechaInicio, this.fechaFin,
				this.estado, this.integrantes, this.subsidios, this.prorrogas,this.programa);
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
	public String getResumen() {
		return this.resumen;
	}

	@Override
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	@Override
	public LocalDate getFechaPresentacion() {
		return this.fechaPresentacion;
	}

	@Override
	public void setFechaPresentacion(LocalDate fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	@Override
	public LocalDate getFechaAprobacion() {
		return this.fechaAprobacion;
	}

	@Override
	public void setFechaAprobacion(LocalDate fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	@Override
	public String getDescripcion() {
		return this.descripcion;
	}

	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public EstadoProyecto getEstado() {
		return this.estado;
	}

	@Override
	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	@Override
	public List<IIntegrante> getIntegrantes() {
		return this.integrantes;
	}

	@Override
	public void agregarIntegrante(IIntegrante integrante) {
		this.integrantes.add(integrante);
	}

	@Override
	public void quitarIntegrante(IIntegrante integrante) {
		this.integrantes.remove(integrante);
	}

	@Override
	public List<ISubsidio> getSubsidios() {
		return this.subsidios;
	}

	@Override
	public void agregarSubsidio(ISubsidio subsidio) {
		this.subsidios.add(subsidio);
	}

	@Override
	public void quitarSubsidio(ISubsidio subsidio) {
		this.subsidios.remove(subsidio);
	}

	@Override
	public List<IProrroga> getProrrogas() {
		return this.prorrogas;
	}

	@Override
	public void agregarProrroga(IProrroga prorroga) {
		this.prorrogas.add(prorroga);
	}

	@Override
	public void quitarProrroga(IProrroga prorroga) {
		this.prorrogas.remove(prorroga);
	}

	@Override
	public IPrograma getPrograma() {
		return programa;
	}

	@Override
	public void setPrograma(IPrograma programa) {
		this.programa = programa;
	}
}