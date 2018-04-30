package modelo.investigacion;

import modelo.docente.IDocente;
import modelo.docente.ICargoDocente;

public class Integrante implements IIntegrante {
    private int id;
    private String apellido;
    private String nombre;
    private String cargo;
    private String institucion;
    private int horasSemanales;

    public Integrante(int id, IDocente docente, ICargoDocente cargoDocente, int horasSemanales) {
        this.setId(id);
        this.apellido = docente.getPersona().getApellido();
        this.nombre = docente.getPersona().getNombre();
        this.cargo = cargoDocente.getCargo().getDescripcion();
        this.institucion = "UNLu";
        this.horasSemanales = horasSemanales;
    }

    public Integrante(String apellido, String nombre, String cargo, String institucion, int horasSemanales) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.cargo = cargo;
        this.institucion = institucion;
        this.horasSemanales = horasSemanales;
    }

    @Override
    public IIntegrante clone() {
        return (IIntegrante) new Integrante(
            this.apellido,
            this.nombre,
            this.cargo,
            this.institucion,
            this.horasSemanales
            );
    }

    @Override
    public String getApellido() {
        return this.apellido;
    }

   // @Override
   // public void setApellidoNombre(String apellido) {
    //    this.apellido = apellido;
   // }

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

    /*@Override
    public String getApellidoNombre() {
        // TODO Auto-generated method stub
        return this.nombre;
    } */

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