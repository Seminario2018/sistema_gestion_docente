package modelo.docente;

import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.cargo.ICargo;
import modelo.persona.IPersona;

public class Docente implements IDocente {
	private IPersona persona;
	private int legajo;
	private String observaciones;
	private CategoriaInvestigacion categoriaInvestigacion;
	private EstadoDocente estado;

	private List<IIncentivo> incentivos;
	private List<ICargo> cargosDocentes;

	@Override
	public IDocente clone() {
		return new Docente(persona, legajo, observaciones, categoriaInvestigacion, estado, incentivos, cargosDocentes);
	}

	public Docente() {

	}

	public Docente(IPersona persona, int legajo, String observaciones, CategoriaInvestigacion categoriaInvestigacion,
			EstadoDocente estado, List<IIncentivo> incentivos,
			List<ICargo> cargosDocentes) {
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
    public List<ICargo> getCargosDocentes() {
		return cargosDocentes;
	}

	@Override
    public void agregarCargoDocente(ICargo planta) {
		// TODO actualizar BD
		this.cargosDocentes.add(planta);
	}

	@Override
    public void quitarCargoDocente(ICargo planta) {
		// TODO actualizar BD
		this.cargosDocentes.remove(planta);
	}

	@Override
	public List<IIncentivo> getIncentivos() {
		// TODO Auto-generated method stub
		return incentivos;
	}


	@Override
	public void agregarIncentivo(IIncentivo incentivo) {
		// TODO Auto-generated method stub
		this.incentivos.add(incentivo);
	}



	@Override
	public void quitarIncentivo(IIncentivo incentivo) {
		// TODO Auto-generated method stub
		this.incentivos.remove(incentivo);
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