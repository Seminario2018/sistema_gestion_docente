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

    public EstadoOperacion guardarPersona(IPersona persona) {
        if (GestorPersona.existePersona(persona)) {
            return gestorPersona.modificarPersona(persona);
        } else {
            return gestorPersona.nuevaPersona(persona);
        }
    }

    public EstadoOperacion eliminarPersona(IPersona persona) {
        return this.gestorPersona.eliminarPersona(persona);
    }

    public List<IPersona> listarPersonas(IPersona persona) {
        return this.gestorPersona.listarPersonas(persona);
    }

    // Contactos: =============================================================
    public EstadoOperacion guardarContacto(IPersona persona, IContacto contacto) {
        if (contacto.getId() == -1) {
            return this.gestorPersona.agregarContacto(persona, contacto);
        } else {
            return this.gestorPersona.modificarContacto(persona, contacto);
        }
    }

    public EstadoOperacion quitarContacto(IPersona persona, IContacto contacto) {
        return this.gestorPersona.quitarContacto(persona, contacto);
    }

    public List<IContacto> getContactos(IPersona persona) {
        return this.gestorPersona.getContactos(persona);
    }

    // Domicilios: ============================================================
    public EstadoOperacion guardarDomicilio(IPersona persona, IDomicilio domicilio) {
        if (domicilio.getId() == -1) {
            return this.gestorPersona.agregarDomicilio(persona, domicilio);
        } else {
            return this.gestorPersona.modificarDomicilio(persona, domicilio);
        }
    }

    public EstadoOperacion quitarDomicilio(IPersona persona, IDomicilio domicilio) {
        return this.gestorPersona.quitarDomicilio(persona, domicilio);
    }

    public List<IDomicilio> getDomicilios(IPersona persona) {
        return this.gestorPersona.getDomicilios(persona);
    }

    // Títulos: ===============================================================
    public EstadoOperacion guardarTitulo(IPersona persona, ITitulo titulo) {
        if (titulo.getId() == -1) {
            return this.gestorPersona.agregarTitulo(persona, titulo);
        } else {
            return this.gestorPersona.modificarTitulo(persona, titulo);
        }
    }

    public EstadoOperacion quitarTitulo(IPersona persona, ITitulo titulo) {
        return this.gestorPersona.quitarTitulo(persona, titulo);
    }

    public List<ITitulo> getTitulos(IPersona persona) {
        return this.gestorPersona.getTitulos(persona);
    }

    // Plantillas vacías: =====================================================
    public IPersona getIPersona() {
        return this.gestorPersona.getIPersona();
    }

    public IContacto getIContacto() {
        return this.gestorPersona.getIContacto();
    }

    public IDomicilio getIDomicilio() {
        return this.gestorPersona.getIDomicilio();
    }

    public ITitulo getITitulo() {
        return this.gestorPersona.getITitulo();
    }

}
