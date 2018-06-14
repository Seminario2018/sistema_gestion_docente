package controlador;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.investigacion.GestorPrograma;
import modelo.investigacion.GestorProyecto;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IPrograma;
import modelo.investigacion.IProrroga;
import modelo.investigacion.IProyecto;
import modelo.investigacion.IRendicion;
import modelo.investigacion.ISubsidio;

public class ControlInvestigacion {

    private GestorPrograma gestorPrograma = new GestorPrograma();
    private GestorProyecto gestorProyecto = new GestorProyecto();

    //    Proyectos
    public EstadoOperacion guardarProyecto(IProyecto proyecto, IPrograma programa) {
        if (proyecto.getId() == -1) {
            return gestorProyecto.nuevoProyecto(proyecto, programa);
        } else {
            return gestorProyecto.modificarProyecto(proyecto, programa);
        }
    }

    public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
        return gestorProyecto.eliminarProyecto(proyecto);
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto) {
        return gestorProyecto.listarProyecto(proyecto, null);
    }

    public IProyecto getIProyecto() {
        return gestorProyecto.getIProyecto();
    }

    public IPrograma getIPrograma() {
        return gestorPrograma.getIPrograma();
    }

    //    Integrantes
    public EstadoOperacion guardarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        return gestorProyecto.AgregarIntegrante(proyecto, integrante);
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        return gestorProyecto.AgregarIntegrante(proyecto, integrante);
    }

    public List<IIntegrante> listarIntegrantes(IProyecto proyecto, IIntegrante integrante) {
        return gestorProyecto.listarIntegrantes(proyecto, integrante);
    }

    public IIntegrante getIIntegrante() {
        return gestorProyecto.getIIntegrante();
    }

    //    Subsidios
    public EstadoOperacion guardarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        return gestorProyecto.agregarSubsidio(proyecto, subsidio);
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        return gestorProyecto.quitarSubsidio(proyecto, subsidio);
    }

    public ISubsidio getISubsidio() {
        return gestorProyecto.getISubsidio();
    }

    public List<Year> fechasSubsidios(IProyecto proyecto) {
        List<Year> fechasSubsidios = new ArrayList<Year>();
        for (ISubsidio subsidio : gestorProyecto.listarSubsidios(proyecto, null)) {
            fechasSubsidios.add(subsidio.getFecha());
        }
        return fechasSubsidios;
    }

    // Rendiciones
    public EstadoOperacion guardarRendicion(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion) {
        return gestorProyecto.agregarRendicion(rendicion, proyecto, subsidio);
    }

    public EstadoOperacion quitarRendicion(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion) {
        return gestorProyecto.quitarRendicion(proyecto, subsidio, rendicion);
    }

    public IRendicion getIRendicion() {
        return gestorProyecto.getIRendicion();
    }

    //    Prorrogas
    public EstadoOperacion guardarProrroga(IProyecto proyecto, IProrroga prorroga) {
        return gestorProyecto.agregarProrroga(proyecto, prorroga);
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        return gestorProyecto.quitarProrroga(proyecto, prorroga);
    }

    public IProrroga getIProrroga() {
        return gestorProyecto.getIProrroga();
    }


}
