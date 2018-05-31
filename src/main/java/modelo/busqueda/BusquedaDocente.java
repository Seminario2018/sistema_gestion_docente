package modelo.busqueda;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class BusquedaDocente {
	private int legajo;
	private String nombre;
	public BusquedaDocente() {
		
	}
	public BusquedaDocente(int legajo, String apellido, String nombre) {
		super();
		this.legajo = legajo;
		this.nombre = apellido + " " + nombre;
	}
	public int getLegajo() {
		return legajo;
	}
	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
