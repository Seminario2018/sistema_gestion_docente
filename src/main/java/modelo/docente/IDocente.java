package modelo.docente;

import java.util.List;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.persona.IPersona;
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
	public void setIncentivos(List<IIncentivo> incentivos);
	public void agregarIncentivo(IIncentivo incentivo);
	public void quitarIncentivo(IIncentivo incentivo);

	public List<ICargoDocente> getCargosDocentes();
	public void setCargosDocentes(List<ICargoDocente> cargosDocentes);
	public void agregarCargoDocente(ICargoDocente cargoDocente);
	public void quitarCargoDocente(ICargoDocente cargoDocente);
	public IPersona getPersona();
	public void setPersona(IPersona persona);

}