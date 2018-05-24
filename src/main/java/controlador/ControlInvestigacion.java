package controlador;

import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.investigacion.GestorProyecto;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IPrograma;
import modelo.investigacion.IProrroga;
import modelo.investigacion.IProyecto;
import modelo.investigacion.ISubsidio;

public class ControlInvestigacion {

    private GestorProyecto gestorProyecto = new GestorProyecto();

    //    Proyectos
    public EstadoOperacion nuevoProyecto(IProyecto proyecto, IPrograma programa) {
        return this.gestorProyecto.nuevoProyecto(proyecto, programa);
    }

    public EstadoOperacion modificarProyecto(IProyecto proyecto, IPrograma programa) {
        return this.gestorProyecto.modificarProyecto(proyecto, programa);
    }

    public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
        return this.gestorProyecto.eliminarProyecto(proyecto);
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto) {
        return this.gestorProyecto.listarProyecto(proyecto);
    }

    public IProyecto getIProyecto() {
        return this.gestorProyecto.getIProyecto();
    }

    //    Integrantes
    public EstadoOperacion agregarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        return this.gestorProyecto.AgregarIntegrante(proyecto, integrante);
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        return this.gestorProyecto.AgregarIntegrante(proyecto, integrante);
    }

    //    Subsidios
    public EstadoOperacion agregarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        return this.gestorProyecto.agregarSubsidio(proyecto, subsidio);
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        return this.gestorProyecto.quitarSubsidio(proyecto, subsidio);
    }

    //    Prorrogas
    public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
        return this.gestorProyecto.agregarProrroga(proyecto, prorroga);
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        return this.gestorProyecto.quitarProrroga(proyecto, prorroga);
    }

}
