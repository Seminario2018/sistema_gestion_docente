package modelo.investigacion;

import modelo.docente.IDocente;
import modelo.docente.IPlanta;

public class Integrante {
	private String apellidoNombre;
	private String cargo;
	private String institucion;
	private int horasSemanales;
	
	public Integrante(IDocente docente, IPlanta planta, int horasSemanales) {
		super();
		this.apellidoNombre = docente.getApellidoNombre();
		this.cargo = planta.getCargo().getDescripcion();
		this.institucion = "UNLu";
		this.horasSemanales = horasSemanales;
	}
}