package modelo.investigacion;

import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;

public class Integrante implements IIntegrante {
    private int id;
    private int legajo;
    private String apellido;
    private String nombre;
    private String cargo;
    private String institucion;
    private int horasSemanales;

    /* Constructor para docentes de la UNLu */
    public Integrante(int id, IDocente docente, ICargoDocente cargoDocente, int horasSemanales) {
        this.id = id;
        this.legajo = docente.getLegajo();
        this.apellido = docente.getPersona().getApellido();
        this.nombre = docente.getPersona().getNombre();
        this.cargo = cargoDocente.getCargo().getDescripcion();
        this.institucion = "UNLu";
        this.horasSemanales = horasSemanales;
    }

    /* Constructor para no docentes / docentes que no son de la UNLu */
    public Integrante(int id, String apellido, String nombre, String cargo, String institucion, int horasSemanales) {
        this.id = id;
        this.apellido = apellido;
        this.nombre = nombre;
        this.cargo = cargo;
        this.institucion = institucion;
        this.horasSemanales = horasSemanales;
    }

    /* Constructor para el clone() */
    public Integrante(int id, int legajo, String apellido, String nombre, String cargo, String institucion,
            int horasSemanales) {
        super();
        this.id = id;
        this.legajo = legajo;
        this.apellido = apellido;
        this.nombre = nombre;
        this.cargo = cargo;
        this.institucion = institucion;
        this.horasSemanales = horasSemanales;
    }

    @Override
    public IIntegrante clone() {
        return (IIntegrante) new Integrante(this.id,
                this.legajo,
                this.apellido,
                this.nombre,
                this.cargo,
                this.institucion,
                this.horasSemanales);
    }

    @Override
    public String getApellido() {
        return this.apellido;
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

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return this.nombre;
    }

    @Override
    public void setNombre(String nombre) {
        // TODO Auto-generated method stub
        this.nombre=nombre;
    }

    @Override
    public void setApellido(String apellido) {
        // TODO Auto-generated method stub
        this.apellido=apellido;

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}