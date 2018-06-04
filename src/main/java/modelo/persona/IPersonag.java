package modelo.persona;

import java.time.LocalDate;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;

public interface IPersonag {
	
	public String getApellido();
	public String getNombre();
	public LocalDate getFechaNacimiento();
	public TipoDocumento getTipoDocumento2();
	public EstadoPersona getEstado2();
	public int getNroDocumento();

}
