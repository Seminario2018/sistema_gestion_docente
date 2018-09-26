package modelo.busqueda;


public class BusquedaUsuario {

	private int documento;
    private String usuario;
	private String nombre;
	
	public BusquedaUsuario(String usuario, int documento, String nombre, String apellido) {
		super();
		if (usuario != null || !"".equals(usuario)) {
			this.usuario = usuario;			
		} else {
			this.usuario = "-";
		}
		this.documento = documento;
		this.nombre = apellido + ", " + nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
