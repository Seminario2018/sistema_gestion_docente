package modelo.docente;

import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.persona.Persona;
import modelo.cargo.*;
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

	public List<IIncentivo> getIncentivos();
	public void agregarIncentivo(IIncentivo incentivo);
	public void quitarIncentivo(IIncentivo incentivo);

	public List<ICargo> getCargosDocentes();
	public void agregarCargoDocente(ICargo cargoDocente);
	public void quitarCargoDocente(ICargo cargoDocente);
	public Persona getPersona();
	
}