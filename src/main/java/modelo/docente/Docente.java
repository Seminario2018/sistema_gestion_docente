package modelo.docente;

import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.persona.IPersona;

public class Docente implements IDocente {
	private IPersona persona;
	private int legajo;
	private String observaciones;
	private CategoriaInvestigacion categoriaInvestigacion;
	private EstadoDocente estado;

	private List<IIncentivo> incentivos;
	private List<ICargoDocente> cargosDocentes;

	@Override
	public IDocente clone() {
		return new Docente(persona, legajo, observaciones, categoriaInvestigacion, estado, incentivos, cargosDocentes);
	}

	public Docente() {

	}

	public Docente(IPersona persona, int legajo, String observaciones, CategoriaInvestigacion categoriaInvestigacion,
			EstadoDocente estado, List<IIncentivo> incentivos,
			List<ICargoDocente> cargosDocentes) {
		super();
		this.persona = persona;
		this.legajo = legajo;
		this.observaciones = observaciones;
		this.categoriaInvestigacion = categoriaInvestigacion;
		this.estado = estado;
		this.incentivos = incentivos;
		this.cargosDocentes = cargosDocentes;
	}




	@Override
    public int getLegajo() {
		return legajo;
	}

	@Override
    public void setLegajo(int legajo) {
		this.legajo = legajo;
	}



	@Override
    public String getObservaciones() {
		return observaciones;
	}

	@Override
    public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
    public CategoriaInvestigacion getCategoriaInvestigacion() {
		return categoriaInvestigacion;
	}

	@Override
    public void setCategoriaInvestigacion(CategoriaInvestigacion categoriaInvestigacion) {
		this.categoriaInvestigacion = categoriaInvestigacion;
	}

	@Override
    public EstadoDocente getEstado() {
		return estado;
	}

	@Override
    public void setEstado(EstadoDocente estado) {
		this.estado = estado;
	}

	@Override
    public List<ICargoDocente> getCargosDocentes() {
		return cargosDocentes;
	}

	@Override
    public void agregarCargoDocente(ICargoDocente planta) {
		this.cargosDocentes.add(planta);
		GestorDocente gd = new GestorDocente();
		gd.agregarCargoDocente(this, planta);
	}

	@Override
    public void quitarCargoDocente(ICargoDocente planta) {
		this.cargosDocentes.remove(planta);
		GestorDocente gd = new GestorDocente();
		gd.quitarCargoDocente(this, planta);
	}

	@Override
	public List<IIncentivo> getIncentivos() {
		return incentivos;
	}


	@Override
	public void agregarIncentivo(IIncentivo incentivo) {
		this.incentivos.add(incentivo);
		GestorDocente gd = new GestorDocente();
		gd.agregarIncentivo(this, incentivo);
	}



	@Override
	public void quitarIncentivo(IIncentivo incentivo) {
		this.incentivos.remove(incentivo);
		GestorDocente gd = new GestorDocente();
		gd.quitarIncentivo(this, incentivo);
	}


	@Override
	public IPersona getPersona() {
		return persona;
	}

	@Override
	public void setPersona(IPersona persona) {
        this.persona = persona;
    }






}