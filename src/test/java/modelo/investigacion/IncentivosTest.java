package modelo.investigacion;

import org.junit.Before;
import org.junit.Test;
import controlador.ControlDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;

public class IncentivosTest {

    private IDocente docenteSeleccionado;
    private ControlDocente controlDocente = new ControlDocente(null);

    @Before
    public void inicializar() {
        IDocente docenteBusqueda = this.controlDocente.getIDocente();
        docenteBusqueda.setLegajo(143191);
        docenteSeleccionado = this.controlDocente.listarDocente(docenteBusqueda).get(0);
    }

    @Test
    public void listarIncentivos() {
        assert (!docenteSeleccionado.getIncentivos().isEmpty()) : "No aparacen los incentivos";
        System.out.printf("-- Incentivos:\n");
        for (IIncentivo incentivo : docenteSeleccionado.getIncentivos()) {
            System.out.printf("--    Año: %s\n", incentivo.getFecha().toString());
        }
    }
}
