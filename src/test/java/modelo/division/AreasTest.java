package modelo.division;

import java.util.List;
import org.junit.Test;
import controlador.ControlDivision;

public class AreasTest {

    @Test public void listaAreas() {
        ControlDivision controlDivision = new ControlDivision(null);
        IArea areaBusqueda = null;
//        IArea areaBusqueda = new Area();
//        areaBusqueda.setCodigo("");
        List<IArea> listaAreas = controlDivision.listarAreas(areaBusqueda);
        assert (listaAreas.size() > 0);
        for (IArea area : listaAreas) {
            System.out.printf("%s\n", area.getDescripcion());
        }
    }

}
