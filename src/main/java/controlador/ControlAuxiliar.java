package controlador;

import java.util.List;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;

public class ControlAuxiliar {

    public static List<TipoCargo> listarTiposCargo() {
        return TipoCargo.getLista();
    }

    public static List<EstadoCargo> listarEstadosCargo() {
        return EstadoCargo.getLista();
    }

}
