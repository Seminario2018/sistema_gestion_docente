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
import vista.controladores.ControladorVista;

public class ControlInvestigacion {

    private GestorPrograma gestorPrograma = new GestorPrograma();
    private GestorProyecto gestorProyecto = new GestorProyecto();
    private ControladorVista vista;

    public ControlInvestigacion(ControladorVista vista) {
        this.vista = vista;
    }

    //    Proyectos
    public EstadoOperacion guardarProyecto(IProyecto proyecto) {
        if (proyecto.getId() == -1) {
            return gestorProyecto.nuevoProyecto(proyecto);
        } else {
            return gestorProyecto.modificarProyecto(proyecto);
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

    //    Programas
    public EstadoOperacion guardarPrograma(IPrograma programa) {
        if (programa.getId() == -1) {
            return gestorPrograma.nuevoPrograma(programa);
        } else {
            return gestorPrograma.modificarPrograma(programa);
        }
    }

    public EstadoOperacion eliminarPrograma(IPrograma programa) {
        return gestorPrograma.eliminarPrograma(programa);
    }

    public EstadoOperacion agregarProyecto(IPrograma programa, IProyecto proyecto) {
        EstadoOperacion resultado = gestorPrograma.agregarProyecto(programa, proyecto);
        switch (resultado.getEstado()) {
            case UPDATE_OK:
                try {
                    if (gestorPrograma.getCantidadProyectos(programa) < 2) {
                        vista.alertaError("Programas", "Asignación de Proyecto a Programa",
                            "El programa tiene menos de 2 proyectos asignados");
                    }
                } catch (RuntimeException e) {
                    vista.alertaError("Programas", "Asignación de Proyecto a Programa", e.getMessage());
                }
                break;
            case UPDATE_ERROR:
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " + resultado.getMensaje());
        }
        return resultado;
    }

    public EstadoOperacion quitarProyecto(IPrograma programa, IProyecto proyecto) {
        EstadoOperacion resultado = gestorPrograma.quitarProyecto(programa, proyecto);
        switch (resultado.getEstado()) {
            case UPDATE_OK:
                try {
                    if (gestorPrograma.getCantidadProyectos(programa) < 2) {
                        vista.alertaError("Programas", "Quita de Proyecto a Programa",
                            "El programa tiene menos de 2 proyectos asignados");
                    }
                } catch (RuntimeException e) {
                    vista.alertaError("Programas", "Quita de Proyecto a Programa", e.getMessage());
                }
                break;
            case UPDATE_ERROR:
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " + resultado.getMensaje());
        }
        return resultado;
    }

    public IPrograma getIPrograma() {
        return gestorPrograma.getIPrograma();
    }

    //    Integrantes
    public EstadoOperacion guardarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        if (integrante.getId() == -1) {
            return gestorProyecto.agregarIntegrante(proyecto, integrante);
        } else {
            return gestorProyecto.modificarIntegrante(proyecto, integrante);
        }
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        return gestorProyecto.quitarIntegrante(proyecto, integrante);
    }

    public List<IIntegrante> listarIntegrantes(IProyecto proyecto, IIntegrante integrante) {
        return gestorProyecto.listarIntegrantes(proyecto, integrante);
    }

    public IIntegrante getIIntegrante() {
        return gestorProyecto.getIIntegrante();
    }

    //    Subsidios
    public EstadoOperacion guardarSubsidio(IProyecto proyecto, ISubsidio subsidio) {

        ISubsidio subsidioBusqueda = gestorProyecto.getISubsidio();
        subsidioBusqueda.setFecha(subsidio.getFecha());
        List<ISubsidio> subsidios = gestorProyecto.listarSubsidios(proyecto, subsidioBusqueda);
        if (subsidios.isEmpty()) {
            return gestorProyecto.agregarSubsidio(proyecto, subsidio);
        } else {
            return gestorProyecto.modificarSubsidio(proyecto, subsidio);
        }
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
        if (rendicion.getId() == -1) {
            return gestorProyecto.agregarRendicion(rendicion, proyecto, subsidio);

        } else {
            IRendicion rendicionBusqueda = gestorProyecto.getIRendicion();
            rendicionBusqueda.setId(rendicion.getId());
            if (gestorProyecto.listarRendiciones(proyecto, subsidio, rendicionBusqueda).isEmpty()) {
                return gestorProyecto.agregarRendicion(rendicion, proyecto, subsidio);
            } else {
                return gestorProyecto.modificarRendicion(proyecto, subsidio, rendicion);
            }
        }
    }

    public EstadoOperacion quitarRendicion(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion) {
        return gestorProyecto.quitarRendicion(proyecto, subsidio, rendicion);
    }

    public IRendicion getIRendicion() {
        return gestorProyecto.getIRendicion();
    }

    //    Prorrogas
    public EstadoOperacion guardarProrroga(IProyecto proyecto, IProrroga prorroga) {
        if (prorroga.getDisposicion() == null || prorroga.getDisposicion().equals("")) {
            return gestorProyecto.agregarProrroga(proyecto, prorroga);
        } else {
            return gestorProyecto.modificarProrroga(proyecto, prorroga);
        }
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        return gestorProyecto.quitarProrroga(proyecto, prorroga);
    }

    public IProrroga getIProrroga() {
        return gestorProyecto.getIProrroga();
    }


}
