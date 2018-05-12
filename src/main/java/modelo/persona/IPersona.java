package modelo.persona;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;

public interface IPersona {
	
	
	public String getApellido();
	public String getNombre();
	public LocalDate getFechaNacimiento();
	public TipoDocumento getTipoDocumento();
	public List<IDomicilio> getDomicilios();
	public List<IContacto> getContactos();
	public EstadoPersona getEstado();
	public int getNroDocumento();
	
	
	public void setApellido(String apellido);
	public void setNombre(String nombre);
	public void setFechaNacimiento(LocalDate fechaNacimiento);
	public void setTipoDocumento(TipoDocumento tipoDocumento);
	public void setDomicilios(List<IDomicilio> domicilio);
	public void setContactos(List<IContacto> contacto);
	public void setEstado(EstadoPersona estado);
	public void setNroDocumento(int nroDocumento);
	
	public IPersona clone();
	public void setTitulos(List<ITitulo> titulos);
	public List<ITitulo> getTitulos();
}
