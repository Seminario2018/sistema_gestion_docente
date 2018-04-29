package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoOperacion {
	public enum CodigoEstado {
		INSERT_OK, INSERT_ERROR,
		UPDATE_OK, UPDATE_ERROR,
		DELETE_OK, DELETE_ERROR,
		SELECT_OK, SELECT_ERROR;
	}
	
	private CodigoEstado estado;
	private String mensaje; 
	
	public EstadoOperacion(CodigoEstado estado, String mensaje) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
	}
	
	public CodigoEstado getEstado() {
		return estado;
	}
	public void setEstado(CodigoEstado estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
