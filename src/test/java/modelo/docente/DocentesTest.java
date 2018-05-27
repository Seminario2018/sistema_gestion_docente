package modelo.docente;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import controlador.ControlAuxiliar;
import controlador.ControlDocente;
import controlador.ControlInvestigacion;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProyecto;
import vista.controladores.Docentes;
import vista.controladores.Docentes.FilaCargo;
import vista.controladores.Docentes.FilaInvestigacion;

public class DocentesTest {

    private ControlDocente controlDocente = new ControlDocente(null);
    ControlInvestigacion controlInvestigacion = new ControlInvestigacion();

    private IDocente docenteSeleccionado;

    @Before
    public void inicializar() {
        IDocente docenteBusqueda = this.controlDocente.getIDocente();
        docenteBusqueda.setLegajo(143191);

        List<IDocente> listaDocentes = this.controlDocente.listarDocente(docenteBusqueda);
        assert (listaDocentes.size() == 1) : "Hay más de un docente encontrado con el mismo legajo.";

        docenteSeleccionado = listaDocentes.get(0);
        assert (docenteSeleccionado != null) : "Docente seleccionado nulo.";
    }

    @Test
    public void cargoDocentes() {
        ICargoDocente cargoDocenteBusqueda = null;
        List<ICargoDocente> listaCargoDocentes = controlDocente.listarCargosDocente(docenteSeleccionado, cargoDocenteBusqueda);

        System.out.printf("\n> CargoDocentes:\n");
        Docentes vistaDocentes = new Docentes();
        for (ICargoDocente cargoDocente : listaCargoDocentes) {
            FilaCargo fila = vistaDocentes.new FilaCargo(cargoDocente);
            System.out.printf("%5d\t%10s\t%10s\t%10s\n", fila.getId(), fila.getArea(), fila.getCargo(), fila.getEstado());
        }
        System.out.println();
    }

    @Test
    public void estadosCargos() {
        List<EstadoCargo> listaEstadosCargos = ControlAuxiliar.listarEstadosCargo();
        assert (!listaEstadosCargos.isEmpty()) : "No hay EstadoCargos disponibles.";

        System.out.printf("\n> EstadoCargos:\n");
        for (EstadoCargo estado : listaEstadosCargos) {
            System.out.printf("%s\n", estado);
        }
        System.out.println();
    }

    @Test
    public void tipoCargos() {
        List<TipoCargo> listaTipoCargos = ControlAuxiliar.listarTiposCargo();

        System.out.printf("\n > TipoCargos:\n");
        for (TipoCargo tipo : listaTipoCargos) {
            System.out.printf("%s\n", tipo);
        }
        System.out.println();
    }

    @Test
    public void investigacion() {
        // Lista con todos los proyectos (?):
        List<IProyecto> listaProyectos = this.controlInvestigacion.listarProyecto(null);
        // Instancia de la vista para instanciar filas para el test:
        Docentes docentes = new Docentes();
        // Lista de filas:
        List<FilaInvestigacion> filasInvestigacion = new ArrayList<FilaInvestigacion>();

        for (IProyecto proyecto : listaProyectos) {
            for (IIntegrante integrante : proyecto.getIntegrantes()) {
                if (integrante.getLegajo() == docenteSeleccionado.getLegajo()) {
                    // Agregar fila a la "tabla" (Falta el área):
                    FilaInvestigacion fila = docentes.new FilaInvestigacion(proyecto.getId(), proyecto.getNombre(), "", integrante.getCargo());
                    filasInvestigacion.add(fila);
                    break;
                }
            }
        }

        // "Mostrar en la tabla"
        for (FilaInvestigacion fila : filasInvestigacion) {
            System.out.printf("%5d\t%10s\t%10s\t%10s\n", fila.getId(), fila.getNombre(), fila.getArea(), fila.getCargo());
        }
    }

    @Test
    public void incentivos() {
        // Categoría investigación:
        CategoriaInvestigacion ci = docenteSeleccionado.getCategoriaInvestigacion();
        System.out.printf("Categoría: %s\n", ci);

        // Incentivos:
        List<IIncentivo> incentivos = docenteSeleccionado.getIncentivos();
        assert (incentivos != null) : "Los incentivos no están setteados";

        System.out.printf("\n> Incentivos:\n");
        for (IIncentivo incentivo : incentivos) {
            System.out.printf("%d\n", incentivo.getFecha().getValue());
        }
        System.out.println();
    }

}
