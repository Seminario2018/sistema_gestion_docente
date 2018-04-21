package modelo.docente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoDocente;
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

	private List<ITitulo> titulos;
	private List<IIncentivo> incentivos;
	private List<IPlanta> planta;

	@Override
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
			CategoriaInvestigacion categoriaInvestigacion, EstadoDocente estado, List<ITitulo> titulos,
			List<IIncentivo> incentivos, List<IPlanta> planta) {
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
		this.titulos = new ArrayList<ITitulo>(titulos);
		this.incentivos = new ArrayList<IIncentivo>(incentivos);
		this.planta = new ArrayList<IPlanta>(planta);
	}

	@Override
    public int getLegajo() {
		return legajo;
	}

	@Override
    public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

	@Override
    public String getApellidoNombre() {
		return apellidoNombre;
	}

	@Override
    public void setApellidoNombre(String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}

	@Override
    public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	@Override
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
    public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	@Override
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Override
    public int getNumDocumento() {
		return numDocumento;
	}

	@Override
    public void setNumDocumento(int numDocumento) {
		this.numDocumento = numDocumento;
	}

	@Override
    public String getDomicilio() {
		return domicilio;
	}

	@Override
    public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	@Override
    public String getLocalidad() {
		return localidad;
	}

	@Override
    public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
    public String getCodigoPostal() {
		return codigoPostal;
	}

	@Override
    public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Override
    public String getTelParticular() {
		return telParticular;
	}

	@Override
    public void setTelParticular(String telParticular) {
		this.telParticular = telParticular;
	}

	@Override
    public String getTelLaboral() {
		return telLaboral;
	}

	@Override
    public void setTelLaboral(String telLaboral) {
		this.telLaboral = telLaboral;
	}

	@Override
    public String getTelCelular() {
		return telCelular;
	}

	@Override
    public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	@Override
    public String getMailPersonal() {
		return mailPersonal;
	}

	@Override
    public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}

	@Override
    public String getMailLaboral() {
		return mailLaboral;
	}

	@Override
    public void setMailLaboral(String mailLaboral) {
		this.mailLaboral = mailLaboral;
	}

	@Override
    public String getObservaciones() {
		return observaciones;
	}

	@Override
    public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
    public CategoriaInvestigacion getCategoriaInvestigacion() {
		return categoriaInvestigacion;
	}

	@Override
    public void setCategoriaInvestigacion(CategoriaInvestigacion categoriaInvestigacion) {
		this.categoriaInvestigacion = categoriaInvestigacion;
	}

	@Override
    public EstadoDocente getEstado() {
		return estado;
	}

	@Override
    public void setEstado(EstadoDocente estado) {
		this.estado = estado;
	}

	@Override
    public List<ITitulo> getTitulos() {
		return titulos;
	}

	@Override
    public void agregarTitulo(ITitulo titulo) {
		// TODO actualizar BD
		this.titulos.add(titulo);
	}

	@Override
    public void quitarTitulo(ITitulo titulo) {
		// TODO actualizar BD
		this.titulos.remove(titulo);
	}

	@Override
    public List<IIncentivo> getIncentivos() {
		return incentivos;
	}

	@Override
    public void agregarIncentivo(IIncentivo incentivo) {
		// TODO actualizar BD
		this.incentivos.add(incentivo);
	}

	@Override
    public void quitarIncentivo(IIncentivo incentivo) {
		// TODO actualizar BD
		this.incentivos.remove(incentivo);
	}

	@Override
    public List<IPlanta> getPlanta() {
		return planta;
	}

	@Override
    public void agregarPlanta(IPlanta planta) {
		// TODO actualizar BD
		this.planta.add(planta);
	}

	@Override
    public void quitarPlanta(IPlanta planta) {
		// TODO actualizar BD
		this.planta.remove(planta);
	}

}