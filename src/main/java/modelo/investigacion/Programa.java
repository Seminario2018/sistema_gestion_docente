package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoPrograma;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

public class Programa implements IPrograma, IProgramag {
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
    	this.id = -1;
	    this.nombre = null;
	    this.disposicion = null;
	    this.director = null;
	    this.codirector = null;
	    this.fechaInicio = null;
	    this.fechaFin = null;
	    this.estado = null;
	    this.proyectos = new ArrayList<IProyecto>();
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
    	if (this.director  == null) {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res =
    				md.select("ProgramasInvestigacion", "Director", "id = " + this.getId());
    		Hashtable<String, String> reg = res.get(0);
    		Docente doc = new Docente();
			doc.setLegajo(Integer.parseInt(reg.get("Director")));
			GestorDocente gd = new GestorDocente();
			doc = (Docente) gd.listarDocentes(doc).get(0);

			this.setDirector(doc);
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
    		ArrayList<Hashtable<String, String>> res =
    				md.select("ProgramasInvestigacion", "Codirector", "id = " + this.getId());
    		Hashtable<String, String> reg = res.get(0);
    		if (!reg.get("Codirector").equals("")) {
    			Docente doc = new Docente();
    			GestorDocente gd = new GestorDocente();
				doc.setLegajo(Integer.parseInt(reg.get("Codirector")));
				doc = (Docente) gd.listarDocentes(doc).get(0);
				this.setCodirector(doc);
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
    public EstadoPrograma getEstado() {
    	if (this.estado == null) {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res =
    				md.select("ProgramasInvestigacion", "Estado", "id = " + this.getId());
    		Hashtable<String, String> reg = res.get(0);
    		EstadoPrograma estado = new EstadoPrograma();
			estado.setId(Integer.parseInt(reg.get("Estado")));
			estado = EstadoPrograma.getEstado(estado);
			this.setEstado(estado);
    	}
        return this.estado;
    }

    @Override
    public void setEstado(EstadoPrograma estado) {
        this.estado = estado;
    }

    @Override
    public List<IProyecto> getProyectos() {
    	if (this.proyectos.isEmpty()) {
			GestorProyecto gp = new GestorProyecto();
			List<IProyecto> proyectos = gp.listarProyecto(null, this);
			for (IProyecto proyecto : proyectos) {
				this.agregarProyecto(proyecto);
			}
		}
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

	@Override
	public IDocente getDirector2() {
		return this.director;
	}

	@Override
	public IDocente getCodirector2() {
		return this.codirector;
	}

	@Override
	public EstadoPrograma getEstado2() {
		return this.estado;
	}

}