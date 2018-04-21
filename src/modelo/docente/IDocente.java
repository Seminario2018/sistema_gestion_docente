package modelo.docente;

import java.time.LocalDate;
import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.TipoDocumento;

public interface IDocente {

	public IDocente clone();
	public int getLegajo();
	public void setLegajo(int legajo);
	public String getApellidoNombre();
	public void setApellidoNombre(String apellidoNombre);
	public LocalDate getFechaNacimiento();
	public void setFechaNacimiento(LocalDate fechaNacimiento);
	public TipoDocumento getTipoDocumento();
	public void setTipoDocumento(TipoDocumento tipoDocumento);
	public int getNumDocumento();
	public void setNumDocumento(int numDocumento);
	public String getDomicilio();
	public void setDomicilio(String domicilio);
	public String getLocalidad();
	public void setLocalidad(String localidad);
	public String getCodigoPostal();
	public void setCodigoPostal(String codigoPostal);
	public String getTelParticular();
	public void setTelParticular(String telParticular);
	public String getTelLaboral();
	public void setTelLaboral(String telLaboral);
	public String getTelCelular();
	public void setTelCelular(String telCelular);
	public String getMailPersonal();
	public void setMailPersonal(String mailPersonal);
	public String getMailLaboral();
	public void setMailLaboral(String mailLaboral);
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

	public List<IPlanta> getPlanta();
	public void agregarPlanta(IPlanta planta);
	public void quitarPlanta(IPlanta planta);

}