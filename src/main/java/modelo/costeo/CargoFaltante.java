package modelo.costeo;

import java.time.LocalDate;

import modelo.docente.ICargoDocente;

public class CargoFaltante implements ICargoFaltante {
	
	private int legajo;
	private String apellido;
	private String nombre;
	private int codigoCargo;
	private float ultimoCosto;
	private LocalDate fechaUltimoCosto;
	//True si esta en sistema - false si esta en costeo
	private boolean tipo;
	
	public static final boolean FALTA_SISTEMA = false;
	public static final boolean FALTA_COSTEO = true;
	
	public CargoFaltante() {}
	
	public CargoFaltante(ICargoDocente cargo) {
		this.legajo = cargo.getDocente().getLegajo();
		this.apellido = cargo.getDocente().getPersona().getApellido();
		this.nombre = cargo.getDocente().getPersona().getNombre();
		this.codigoCargo = cargo.getId();
		this.ultimoCosto = cargo.getUltimoCosto();
		this.fechaUltimoCosto = cargo.getFechaUltCost();
	}

	public void setCargo(ICargoDocente cargo) {
		this.legajo = cargo.getDocente().getLegajo();
		this.apellido = cargo.getDocente().getPersona().getApellido();
		this.nombre = cargo.getDocente().getPersona().getNombre();
		this.codigoCargo = cargo.getId();
		this.ultimoCosto = cargo.getUltimoCosto();
		this.fechaUltimoCosto = cargo.getFechaUltCost();
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
	public int getCodigoCargo() {
		return codigoCargo;
	}
	public void setCodigoCargo(int codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
	public float getUltimoCosto() {
		return ultimoCosto;
	}
	public void setUltimoCosto(float ultimoCosto) {
		this.ultimoCosto = ultimoCosto;
	}
	public LocalDate getFechaUltimoCosto() {
		return fechaUltimoCosto;
	}
	public void setFechaUltimoCosto(LocalDate fechaUltimoCosto) {
		this.fechaUltimoCosto = fechaUltimoCosto;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	
	
	

}
