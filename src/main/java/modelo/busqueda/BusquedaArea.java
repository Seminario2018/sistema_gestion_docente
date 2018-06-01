package modelo.busqueda;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de jun. de 2018
 */
public class BusquedaArea {
	private String codigo;
	private String descripcion;
	public BusquedaArea() {
		super();
	}
	public BusquedaArea(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
