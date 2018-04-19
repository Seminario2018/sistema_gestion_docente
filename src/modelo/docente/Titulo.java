package modelo.docente;


public class Titulo implements ITitulo {

	private int id;
	private String nombre;

	public Titulo(int id, String nombre) {
	    this.id = id;
	    this.nombre = nombre;
	}

    @Override
    public ITitulo clone() {
        return (ITitulo) new Titulo(
            this.id,
            this.nombre
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

}