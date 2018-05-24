package modelo.cargo;

import java.util.List;
import org.junit.Test;
import controlador.ControlCargo;

public class CargosTest {

    @Test
    public void listaCargos() {
        ControlCargo controlCargo = new ControlCargo();
        ICargo cargoBusqueda = null;
        List<ICargo> listaCargos = controlCargo.listarCargo(cargoBusqueda);
        assert(!listaCargos.isEmpty());

        for (ICargo cargo : listaCargos) {
            System.out.printf("%d\t| %s\n", cargo.getCodigo(), cargo.getDescripcion());
        }
    }

}
