package modelo.persona;


public class Titulo implements ITitulo {

	private int id;
	private String nombre;
	private boolean esMayor;

	public Titulo(int id, String nombre, boolean esMayor) {
	    this.id = id;
	    this.nombre = nombre;
	    this.esMayor = esMayor;
	}

    public Titulo() {
        this.id = -1;
    }


    @Override
    public ITitulo clone() {
        return (ITitulo) new Titulo(
            this.id,
            this.nombre,
            this.esMayor
            );
    }

    @Override
    public int getId() {
        return this.id;
    }

	@Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean isEsMayor() {
		return esMayor;
	}

	@Override
    public void setEsMayor(boolean esMayor) {
		this.esMayor = esMayor;
	}

}