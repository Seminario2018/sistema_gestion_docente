package modelo.investigacion;

import modelo.docente.IDocente;
import modelo.docente.IPlanta;

public class Integrante implements IIntegrante {
	private String apellidoNombre;
	private String cargo;
	private String institucion;
	private int horasSemanales;

	public Integrante(IDocente docente, IPlanta planta, int horasSemanales) {
		this.apellidoNombre = docente.getApellidoNombre();
		this.cargo = planta.getCargo().getDescripcion();
		this.institucion = "UNLu";
		this.horasSemanales = horasSemanales;
	}

	public Integrante(String apellidoNombre, String cargo, int horasSemanales) {
	    this.apellidoNombre = apellidoNombre;
        this.cargo = cargo;
        this.institucion = "UNLu";
        this.horasSemanales = horasSemanales;
	}

    @Override
    public IIntegrante clone() {
        return (IIntegrante) new Integrante(
            this.apellidoNombre,
            this.cargo,
            this.horasSemanales
            );
    }

    @Override
    public String getApellidoNombre() {
        return this.apellidoNombre;
    }

    @Override
    public void setApellidoNombre(IDocente docente) {
        this.apellidoNombre = docente.getApellidoNombre();
    }

    @Override
    public String getCargo() {
        return this.cargo;
    }

    @Override
    public void setCargo(IPlanta planta) {
        this.cargo = planta.getCargo().getDescripcion();
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