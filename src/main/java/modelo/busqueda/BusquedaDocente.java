package modelo.busqueda;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class BusquedaDocente {
	private String legajo;
	private int documento;
	private String nombre;
	
	public BusquedaDocente() {
		
	}
	public BusquedaDocente(String legajo, int documento, String apellido, String nombre) {
		super();
		if (legajo == null || "".equals(legajo)) {
			this.legajo = "-";
		} else {
			this.legajo = legajo;
		}
		this.documento = documento;
		this.nombre = apellido + ", " + nombre;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public int getDocumento() {
		return documento;
	}
	public void setDocumento(int documento) {
		this.documento = documento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
