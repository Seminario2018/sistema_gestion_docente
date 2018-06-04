package modelo.docente;

import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.persona.IPersona;

public interface IDocenteg {
	
	public int getLegajo();
	public String getObservaciones();
	
	public CategoriaInvestigacion getCategoriaInvestigacion2();
	public EstadoDocente getEstado2();
	public IPersona getPersona2();
	
	public List<IIncentivo> getIncentivos2();
	public List<ICargoDocente> getCargosDocentes2();


}
