package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoOperacion {
	public enum CodigoEstado {
		OP_OK, OP_ERROR,
		CONNECT_OK, CONNECT_ERROR,
		INSERT_OK, INSERT_ERROR,
		UPDATE_OK, UPDATE_ERROR,
		DELETE_OK, DELETE_ERROR,
		SELECT_OK, SELECT_ERROR;
	}

	private CodigoEstado estado;
	private String mensaje;
	private Object respuesta;

	public EstadoOperacion(CodigoEstado estado, String mensaje) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
	}
	
	public EstadoOperacion(CodigoEstado estado, String mensaje, Object respuesta) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
		this.respuesta = respuesta;
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
	public Object getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(Object respuesta) {
		this.respuesta = respuesta;
	}

	@Override
    public String toString() {
        return this.mensaje;
    }
}
