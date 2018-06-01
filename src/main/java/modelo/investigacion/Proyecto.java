package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoProyecto;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

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

	private List<IIntegrante> integrantes;
	private List<ISubsidio> subsidios;
	private List<IProrroga> prorrogas;

	public Proyecto(){
		this.id = -1;
		this.nombre = null;
		this.resumen = null;
		this.fechaPresentacion = null;
		this.fechaAprobacion = null;
		this.descripcion = null;
		this.director = null;
		this.codirector = null;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.estado = null;
		this.integrantes = new ArrayList<IIntegrante>(integrantes);
		this.subsidios = new ArrayList<ISubsidio>(subsidios);
		this.prorrogas = new ArrayList<IProrroga>(prorrogas);
	}
	public Proyecto(int id, String nombre, String resumen, LocalDate fechaPresentacion, LocalDate fechaAprobacion,
			String descripcion, IDocente director, IDocente codirector,
			LocalDate fechaInicio, LocalDate fechaFin, EstadoProyecto estado, List<IIntegrante> integrantes,
			List<ISubsidio> subsidios, List<IProrroga> prorrogas) {
		super();
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
				this.estado, this.integrantes, this.subsidios, this.prorrogas);
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
		if (this.director == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("Proyectos", "Director", "id = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
            Docente profesor = new Docente(null, Integer.parseInt(reg.get("Director")), null, null, null, null, null);
            GestorDocente gd = new GestorDocente();
            profesor = (Docente) gd.listarDocentes(profesor).get(0);
            this.setDirector(profesor);
		}
		return this.director;
	}

	@Override
	public void setDirector(IDocente director) {
		this.director = director;
	}

	@Override
	public IDocente getCodirector() {
		if (this.codirector == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("Proyectos", "Codirector", "id = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
			if (!reg.get("Codirector").equals("")) {
            	Docente profesor = new Docente(null, Integer.parseInt(reg.get("Codirector")), null, null, null, null, null);
                GestorDocente gd = new GestorDocente();
                profesor = (Docente) gd.listarDocentes(profesor).get(0);
                this.setCodirector(profesor);
            }
		}
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
		if (this.estado == null) {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select("Proyectos", "Estado", "id = " + this.getId());
			Hashtable<String, String> reg = res.get(0);
			EstadoProyecto est = new EstadoProyecto();
            est.setId(Integer.parseInt(reg.get("Estado")));
            est = EstadoProyecto.getEstado(est);
            this.setEstado(est);
		}
		return this.estado;
	}

	@Override
	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	@Override
	public List<IIntegrante> getIntegrantes() {
		if (this.integrantes.isEmpty()) {
			GestorProyecto gp = new GestorProyecto();
			for (IIntegrante integrante : gp.listarIntegrantes(this, null)) {
				this.agregarIntegrante(integrante);
			}
		}
		
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
		if (this.subsidios.isEmpty()) {
			GestorProyecto gp = new GestorProyecto();
			for (ISubsidio subsidio : gp.listarSubsidios(this, null)) {
            	this.agregarSubsidio(subsidio);
            }
		}
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
		if (this.prorrogas.isEmpty()) {
			GestorProyecto gp = new GestorProyecto();
			for (IProrroga pro : gp.listarProrrogas(this, null)) {
            	this.agregarProrroga(pro);
            }
		}
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
}