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
	private String costoAnt;
	private String fechaCostoAnt;
	private String costoAct;
	private String fechaCostoAct;
	
	public FilaCostoComparar(ICargoDocente cargoAnterior, ICargoFaltante cargoActual) {
		this.legajo = cargoAnterior.getDocente().getLegajo();
		this.apellido = cargoAnterior.getDocente().getPersona().getApellido();
		this.nombre = cargoAnterior.getDocente().getPersona().getNombre();
		this.codigo = cargoAnterior.getId();
		this.costoAnt = Utilidades.floatToString(cargoAnterior.getUltimoCosto());
		this.fechaCostoAnt = Utilidades.localDateToString(cargoAnterior.getFechaUltCost());
		this.costoAct = Utilidades.floatToString(cargoActual.getUltimoCosto());
		this.fechaCostoAct = Utilidades.localDateToString(cargoActual.getFechaUltimoCosto());
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

	public String getCostoAnt() {
		return costoAnt;
	}

	public void setCostoAnt(String costoAnt) {
		this.costoAnt = costoAnt;
	}

	public String getFechaCostoAnt() {
		return fechaCostoAnt;
	}

	public void setFechaCostoAnt(String fechaCostoAnt) {
		this.fechaCostoAnt = fechaCostoAnt;
	}

	public String getCostoAct() {
		return costoAct;
	}

	public void setCostoAct(String costoAct) {
		this.costoAct = costoAct;
	}

	public String getFechaCostoAct() {
		return fechaCostoAct;
	}

	public void setFechaCostoAct(String fechaCostoAct) {
		this.fechaCostoAct = fechaCostoAct;
	}

	
	
}
