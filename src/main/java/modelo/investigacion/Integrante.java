package modelo.investigacion;

import modelo.docente.IDocente;
import modelo.docente.ICargoDocente;

public class Integrante implements IIntegrante {
	private String apellido;
	private String nombre;
	private String cargo;
	private String institucion;
	private int horasSemanales;

	public Integrante(IDocente docente, ICargoDocente cargoDocente, int horasSemanales) {
		this.apellido = docente.getPersona().getApellido();
		this.nombre = docente.getPersona().getNombre();
		this.cargo = cargoDocente.getCargo().getDescripcion();
		this.institucion = "UNLu";
		this.horasSemanales = horasSemanales;
	}

	public Integrante(String apellido, String nombre, String cargo, String institucion, int horasSemanales) {;
	    this.apellido = apellido;
	    this.nombre = nombre;
        this.cargo = cargo;
        this.institucion = institucion;
        this.horasSemanales = horasSemanales;
	}

    @Override
    public IIntegrante clone() {
        return (IIntegrante) new Integrante(
            this.apellidoNombre,
            this.cargo,
            this.institucion,
            this.horasSemanales
            );
    }

    @Override
    public String getApellidoNombre() {
        return this.apellidoNombre;
    }

    @Override
    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    @Override
    public String getCargo() {
        return this.cargo;
    }

    @Override
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String getInstitucion() {
        return this.institucion;
    }

    @Override
    public int getHorasSemanales() {
        return this.horasSemanales;
    }

    @Override
    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }
}