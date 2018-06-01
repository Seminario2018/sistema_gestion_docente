package modelo.busqueda;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de jun. de 2018
 */
public class BusquedaCargo {
	private int codigo;
	private String descripcion;
	public BusquedaCargo() {
		super();
	}
	public BusquedaCargo(int codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
