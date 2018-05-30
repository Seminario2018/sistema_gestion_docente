package controlador;

import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.persona.GestorPersona;
import modelo.persona.IContacto;
import modelo.persona.IDomicilio;
import modelo.persona.IPersona;
import modelo.persona.ITitulo;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class ControlPersona {

    private GestorPersona gestorPersona = new GestorPersona();

    public EstadoOperacion nuevaPersona(IPersona persona) {
        return this.gestorPersona.nuevaPersona(persona);
    }

    public EstadoOperacion modificarPersona(IPersona persona) {
        return this.gestorPersona.eliminarPersona(persona);
    }

    public EstadoOperacion eliminarPersona(IPersona persona) {
        return this.gestorPersona.eliminarPersona(persona);
    }

    public List<IPersona> listarPersonas(IPersona persona) {
        return this.gestorPersona.listarPersonas(persona);
    }

    public List<IDomicilio> getDomicilios(IPersona persona) {
        return this.gestorPersona.getDomicilios(persona);
    }

    public List<IContacto> getContactos(IPersona persona) {
        return this.gestorPersona.getContactos(persona);
    }

    public List<ITitulo> getTitulos(IPersona persona) {
        return this.gestorPersona.getTitulos(persona);
    }

    public IPersona getIPersona() {
        return this.gestorPersona.getIPersona();
    }


    public IContacto getIContacto() {
        return this.gestorPersona.getIContacto();
    }

}
