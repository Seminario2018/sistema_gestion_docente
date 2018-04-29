package modelo.persona;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;

public interface IPersona {
	
	
	public String getApellido();
	public String getNombre();
	public LocalDate getFechaNacimiento;
	public TipoDocumento getTipoDocumento;
	public String getDocumento;
	public List<Domicilio> getDomicilios();
	public List<Contacto> getContactos();
	public EstadoPersona estado;
	
	
	private String apellido;
	private String nombre;
	private LocalDate fechaNacimiento;
	private TipoDocumento tipoDocumento;
	private String documento;
	private List<Domicilio> domicilios;
	private List<Contacto> contactos;
	private EstadoPersona estado;

}
