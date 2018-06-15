package modelo.costeo;

import java.time.LocalDate;

import modelo.docente.ICargoDocente;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 14 de jun. de 2018
 */
public class FilaCostoComparar {
	private int legajo;
	private String apellido;
	private String nombre;
	private int codigo;
	private float costoAnt;
	private LocalDate fechaCostoAnt;
	private float costoAct;
	private LocalDate fechaCostoAct;
	
	public FilaCostoComparar(ICargoDocente cargoAnterior, ICargoDocente cargoActual) {
		this.legajo = cargoAnterior.getDocente().getLegajo();
		this.apellido = cargoAnterior.getDocente().getPersona().getApellido();
		this.nombre = cargoAnterior.getDocente().getPersona().getNombre();
		this.codigo = cargoAnterior.getId();
		this.costoAnt = cargoAnterior.getUltimoCosto();
		this.fechaCostoAnt = cargoAnterior.getFechaUltCost();
		this.costoAct = cargoActual.getUltimoCosto();
		this.fechaCostoAct = cargoActual.getFechaUltCost();
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

	public float getCostoAnt() {
		return costoAnt;
	}

	public void setCostoAnt(float costoAnt) {
		this.costoAnt = costoAnt;
	}

	public LocalDate getFechaCostoAnt() {
		return fechaCostoAnt;
	}

	public void setFechaCostoAnt(LocalDate fechaCostoAnt) {
		this.fechaCostoAnt = fechaCostoAnt;
	}

	public float getCostoAct() {
		return costoAct;
	}

	public void setCostoAct(float costoAct) {
		this.costoAct = costoAct;
	}

	public LocalDate getFechaCostoAct() {
		return fechaCostoAct;
	}

	public void setFechaCostoAct(LocalDate fechaCostoAct) {
		this.fechaCostoAct = fechaCostoAct;
	}
	
}
