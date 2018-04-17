package modelo.usuario;


public class Permiso implements IPermiso {
	private final boolean ACTIVO = true;
	private final boolean INACTIVO = false;
	
	private Modulo modulo;
	private boolean crear;
	private boolean modificar;
	private boolean eliminar;
	private boolean listar;
	
	public Permiso(Modulo modulo) {
		super();
		this.modulo = modulo;
		this.crear = INACTIVO;
		this.modificar = INACTIVO;
		this.eliminar = INACTIVO;
		this.listar = INACTIVO;
	}
}