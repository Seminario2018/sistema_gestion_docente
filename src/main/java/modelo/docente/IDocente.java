package modelo.docente;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.ITitulo;

public interface IDocente {

	public IDocente clone();
	public int getLegajo();
	public void setLegajo(int legajo);
	public String getObservaciones();
	public void setObservaciones(String observaciones);
	public CategoriaInvestigacion getCategoriaInvestigacion();
	public void setCategoriaInvestigacion(
			CategoriaInvestigacion categoriaInvestigacion);
	public EstadoDocente getEstado();
	public void setEstado(EstadoDocente estado);

	public List<ITitulo> getTitulos();
	public void agregarTitulo(ITitulo titulo);
	public void quitarTitulo(ITitulo titulo);

	public List<IIncentivo> getIncentivos();
	public void agregarIncentivo(IIncentivo incentivo);
	public void quitarIncentivo(IIncentivo incentivo);

	public List<ICargoDocente> getPlanta();
	public void agregarPlanta(ICargoDocente planta);
	public void quitarPlanta(ICargoDocente planta);
	
}