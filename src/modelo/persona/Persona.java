package modelo.persona;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 28 de abr. de 2018
 */
public class Persona {

	private String apellido;
	private String nombre;
	private LocalDate fechaNacimiento;
	private TipoDocumento tipoDocumento;
	private String documento;
	private List<Domicilio> domicilios;
	private List<Contacto> contactos;
	private EstadoPersona estado;
	
}
