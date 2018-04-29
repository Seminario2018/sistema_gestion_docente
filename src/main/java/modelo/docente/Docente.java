package modelo.docente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.Persona;

public class Docente implements IDocente {

	private Persona persona;
	private int legajo;
	private String observaciones;
	private CategoriaInvestigacion categoriaInvestigacion;
	private EstadoDocente estado;

	private List<ITitulo> titulos;
	private List<IIncentivo> incentivos;
	private List<ICargoDocente> cargosDocentes;

	@Override
	public IDocente clone() {
		return new Docente(persona, legajo, observaciones, categoriaInvestigacion, estado, titulos, incentivos, cargosDocentes);
	}

	

	public Docente(Persona persona, int legajo, String observaciones, CategoriaInvestigacion categoriaInvestigacion,
			EstadoDocente estado, List<ITitulo> titulos, List<IIncentivo> incentivos,
			List<ICargoDocente> cargosDocentes) {
		super();
		this.persona = persona;
		this.legajo = legajo;
		this.observaciones = observaciones;
		this.categoriaInvestigacion = categoriaInvestigacion;
		this.estado = estado;
		this.titulos = titulos;
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
    public List<ITitulo> getTitulos() {
		return titulos;
	}

	@Override
    public void agregarTitulo(ITitulo titulo) {
		// TODO actualizar BD
		this.titulos.add(titulo);
	}


	

	@Override
    public List<ICargoDocente> getPlanta() {
		return cargosDocentes;
	}

	@Override
    public void agregarPlanta(ICargoDocente planta) {
		// TODO actualizar BD
		this.cargosDocentes.add(planta);
	}

	@Override
    public void quitarPlanta(ICargoDocente planta) {
		// TODO actualizar BD
		this.cargosDocentes.remove(planta);
	}



	@Override
	public void quitarTitulo(ITitulo titulo) {
		// TODO Auto-generated method stub
		
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
	
	
	

}