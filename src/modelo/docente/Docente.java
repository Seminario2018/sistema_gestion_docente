package modelo.docente;

import java.time.LocalDate;
import java.util.ArrayList;

import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.TipoDocumento;

public class Docente implements IDocente {

	private int legajo;
	private String apellidoNombre;
	private LocalDate fechaNacimiento;
	private TipoDocumento tipoDocumento;
	private int numDocumento;
	private String domicilio;
	private String localidad;
	private String codigoPostal;
	private String telParticular;
	private String telLaboral;
	private String telCelular;
	private String mailPersonal;
	private String mailLaboral;
	private String observaciones;
	private CategoriaInvestigacion categoriaInvestigacion;
	private EstadoDocente estado;
	
	private ArrayList<ITitulo> titulos;
	private ArrayList<IIncentivo> incentivos;
	private ArrayList<IPlanta> planta;

	public IDocente clone() {
		return (IDocente) new Docente(
				this.legajo,
				this.apellidoNombre,
				this.fechaNacimiento,
				this.tipoDocumento,
				this.numDocumento,
				this.domicilio,
				this.localidad,
				this.codigoPostal,
				this.telParticular,
				this.telLaboral,
				this.telCelular,
				this.mailPersonal,
				this.mailLaboral,
				this.observaciones,
				this.categoriaInvestigacion,
				this.estado,
				this.titulos,
				this.incentivos,
				this.planta
				);
	}
	
	public Docente(int legajo, String apellidoNombre, LocalDate fechaNacimiento, TipoDocumento tipoDocumento,
			int numDocumento, String domicilio, String localidad, String codigoPostal, String telParticular,
			String telLaboral, String telCelular, String mailPersonal, String mailLaboral, String observaciones,
			CategoriaInvestigacion categoriaInvestigacion, EstadoDocente estado, ArrayList<ITitulo> titulos,
			ArrayList<IIncentivo> incentivos, ArrayList<IPlanta> planta) {
		super();
		this.legajo = legajo;
		this.apellidoNombre = apellidoNombre;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoDocumento = tipoDocumento;
		this.numDocumento = numDocumento;
		this.domicilio = domicilio;
		this.localidad = localidad;
		this.codigoPostal = codigoPostal;
		this.telParticular = telParticular;
		this.telLaboral = telLaboral;
		this.telCelular = telCelular;
		this.mailPersonal = mailPersonal;
		this.mailLaboral = mailLaboral;
		this.observaciones = observaciones;
		this.categoriaInvestigacion = categoriaInvestigacion;
		this.estado = estado;
		this.titulos = titulos;
		this.incentivos = incentivos;
		this.planta = planta;
	}

	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

	public String getApellidoNombre() {
		return apellidoNombre;
	}

	public void setApellidoNombre(String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(int numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelParticular() {
		return telParticular;
	}

	public void setTelParticular(String telParticular) {
		this.telParticular = telParticular;
	}

	public String getTelLaboral() {
		return telLaboral;
	}

	public void setTelLaboral(String telLaboral) {
		this.telLaboral = telLaboral;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	public String getMailPersonal() {
		return mailPersonal;
	}

	public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}

	public String getMailLaboral() {
		return mailLaboral;
	}

	public void setMailLaboral(String mailLaboral) {
		this.mailLaboral = mailLaboral;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public CategoriaInvestigacion getCategoriaInvestigacion() {
		return categoriaInvestigacion;
	}

	public void setCategoriaInvestigacion(CategoriaInvestigacion categoriaInvestigacion) {
		this.categoriaInvestigacion = categoriaInvestigacion;
	}

	public EstadoDocente getEstado() {
		return estado;
	}

	public void setEstado(EstadoDocente estado) {
		this.estado = estado;
	}

	public ArrayList<ITitulo> getTitulos() {
		return titulos;
	}
	
	public void agregarTitulo(ITitulo titulo) {
		// TODO actualizar BD
		this.titulos.add(titulo);
	}
	
	public void quitarTitulo(ITitulo titulo) {
		// TODO actualizar BD
		this.titulos.remove(titulo);
	}

	public ArrayList<IIncentivo> getIncentivos() {
		return incentivos;
	}
	
	public void agregarIncentivo(IIncentivo incentivo) {
		// TODO actualizar BD
		this.incentivos.add(incentivo);
	}
	
	public void quitarIncentivo(IIncentivo incentivo) {
		// TODO actualizar BD
		this.incentivos.remove(incentivo);
	}

	public ArrayList<IPlanta> getPlanta() {
		return planta;
	}
	
	public void agregarPlanta(IPlanta planta) {
		// TODO actualizar BD
		this.planta.add(planta);
	}
	
	public void quitarPlanta(IPlanta planta) {
		// TODO actualizar BD
		this.planta.remove(planta);
	}

}