package modelo.busqueda;


public class BusquedaRole {
	private int id;
	private String nombre;

	public BusquedaRole() {

	}

	public BusquedaRole(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
