package controlador;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.docente.GestorCargosFaltantes;
import modelo.docente.ICargoFaltante;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 12 de jun. de 2018
 */
public class ControlImportarCosto {

	private GestorCargosFaltantes gestorCargosFaltantes = new GestorCargosFaltantes();

//  Cargos faltantes
    public ICargoFaltante getICargoFaltante() {
        return this.gestorCargosFaltantes.getICargoFaltante();
    }

    public EstadoOperacion guardarCargoFaltante(ICargoFaltante cargo) {
        return this.gestorCargosFaltantes.agregarCargoFaltante(cargo);
    }

    public EstadoOperacion eliminarCargoFaltante(ICargoFaltante cargo) {
        return this.gestorCargosFaltantes.eliminarCargoFaltante(cargo);
    }

    public List<ICargoFaltante> listarCargosFaltantes() {
        return this.gestorCargosFaltantes.listarCargosFaltantes();
    }
}
