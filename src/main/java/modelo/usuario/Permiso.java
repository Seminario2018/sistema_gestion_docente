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

    @Override
    public IPermiso clone() {
        return (IPermiso) new Permiso(this.modulo);
    }

    @Override
    public Modulo getModulo() {
        return this.modulo;
    }

    @Override
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @Override
    public boolean getCrear() {
        return this.crear;
    }

    @Override
    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    @Override
    public boolean getModificar() {
        return this.modificar;
    }

    @Override
    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    @Override
    public boolean getEliminar() {
        return this.eliminar;
    }

    @Override
    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    @Override
    public boolean getListar() {
        return this.listar;
    }

    @Override
    public void setListar(boolean listar) {
        this.listar = listar;
    }

}