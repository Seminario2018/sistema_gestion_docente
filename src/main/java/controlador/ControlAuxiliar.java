package controlador;

import java.util.List;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.EstadoPrograma;
import modelo.auxiliares.EstadoProyecto;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;

public class ControlAuxiliar {

    public static List<CategoriaInvestigacion> listarCategoriasInvestigacion() {
        return CategoriaInvestigacion.getLista();
    }

    public static List<EstadoCargo> listarEstadosCargo() {
        return EstadoCargo.getLista();
    }

    public static List<EstadoDocente> listarEstadosDocente() {
        return EstadoDocente.getLista();
    }

    public static List<EstadoPersona> listarEstadosPersona() {
        return EstadoPersona.getLista();
    }

    public static List<EstadoPrograma> listarEstadosPrograma() {
        return EstadoPrograma.getLista();
    }

    public static List<EstadoProyecto> listarEstadosProyecto() {
        return EstadoProyecto.getLista();
    }

    public static List<TipoCargo> listarTiposCargo() {
        return TipoCargo.getLista();
    }

    public static List<TipoContacto> listarTiposContacto() {
        return TipoContacto.getLista();
    }

    public static List<TipoDocumento> listarTiposDocumento() {
        return TipoDocumento.getLista();
    }

}
