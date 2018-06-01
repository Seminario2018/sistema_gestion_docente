package modelo.busqueda;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de jun. de 2018
 */
public class BusquedaPersona {
	private int documento;
	private String nombre;
	public BusquedaPersona() {
		
	}
	public BusquedaPersona(int documento, String apellido, String nombre) {
		super();
		this.documento = documento;
		this.nombre = apellido + ", " + nombre;
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
