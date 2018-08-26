package modelo.costeo;

import modelo.docente.ICargoDocente;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 14 de jun. de 2018
 */
public class FilaCostoComparar {
	private int legajo;
	private String apellido;
	private String nombre;
	private int codigo;
	private String costoant;
	private String fechacostoant;
	private String costoact;
	private String fechacostoact;
	
	public FilaCostoComparar(ICargoDocente cargoAnterior, ICargoFaltante cargoActual) {
		this.legajo = cargoAnterior.getDocente().getLegajo();
		this.apellido = cargoAnterior.getDocente().getPersona().getApellido();
		this.nombre = cargoAnterior.getDocente().getPersona().getNombre();
		this.codigo = cargoAnterior.getId();
		this.costoant = Utilidades.floatToString(cargoAnterior.getUltimoCosto());
		this.fechacostoant = Utilidades.localDateToString(cargoAnterior.getFechaUltCost());
		this.costoact = Utilidades.floatToString(cargoActual.getUltimoCosto());
		this.fechacostoact = Utilidades.localDateToString(cargoActual.getFechaUltimoCosto());
	}

	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCostoant() {
		return costoant;
	}

	public void setCostoant(String costoant) {
		this.costoant = costoant;
	}

	public String getFechacostoant() {
		return fechacostoant;
	}

	public void setFechacostoant(String fechacostoant) {
		this.fechacostoant = fechacostoant;
	}

	public String getCostoact() {
		return costoact;
	}

	public void setCostoact(String costoact) {
		this.costoact = costoact;
	}

	public String getFechacostoact() {
		return fechacostoact;
	}

	public void setFechacostoact(String fechacostoact) {
		this.fechacostoact = fechacostoact;
	}

	
}
