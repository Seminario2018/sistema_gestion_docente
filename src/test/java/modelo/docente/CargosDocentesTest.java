package modelo.docente;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import controlador.ControlDocente;

public class CargosDocentesTest {

    private ControlDocente controlDocente = new ControlDocente(null);
    private IDocente docenteSeleccion = null;

    @Before
    public void inicializar() {
        /* TEST Docentes: Selección de docente */
        // Recupera al docente legajo 143191
        IDocente docenteBusqueda = this.controlDocente.getIDocente();
        docenteBusqueda.setLegajo(143191);
        docenteSeleccion = this.controlDocente.listarDocente(docenteBusqueda)
            .get(0);
        //*/
    }

    @Test
    public void test() {
        // fail("Not yet implemented");
        if (docenteSeleccion != null) {
            List<ICargoDocente> listaCD = this.controlDocente
                .listarCargosDocente(docenteSeleccion, null);
            for (ICargoDocente cd : listaCD) {
                // Muestro los cargosDocente en la tabla:
                System.out.printf("CargosDocentesTest:\n%d\n\t%s\n\t%s\n\t%s\n",
                    cd.getId(),
                    cd.getArea().getDescripcion(),
                    cd.getCargo().getDescripcion(),
                    cd.getEstado().getDescripcion());
            }
        }
    }

}
