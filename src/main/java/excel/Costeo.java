package excel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import controlador.ControlDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;

/**
 * Clase que importa el costeo de una planilla Excel.
 * @author LeoAM
 */
public class Costeo {

    private static ControlDocente ctrlDocente = new ControlDocente(null);

    /** Trae de la base de datos el cargoDocente correspondiente.
     * @param id Id del cargo docente a buscar.
     * @return El cargoDocente buscado.
     * @throws Exception Si el cargoDocente no está en la base de datos.
     * */
    private static ICargoDocente buscarCargoDocente(int id) throws Exception {
        ICargoDocente cargoDocenteBusqueda = ctrlDocente.getICargoDocente();
        cargoDocenteBusqueda.setId(id);

        List<ICargoDocente> listaCargosDocentes = ctrlDocente.listarCargosDocente(null, cargoDocenteBusqueda);
        if (listaCargosDocentes.isEmpty()) {
            throw new Exception("CargoDocente no encontrado.");
        } else {
            return listaCargosDocentes.get(0);
        }
    }

    /**
     * Actualiza el último costo del cargoDocente, y lo persiste en la base de datos.
     * @param cargoDocente CargoDocente a actualizar.
     * @param costo Nuevo costo del cargodocente.
     * @param legajoDocente Legajo del docente.
     * */
    private static void actualizarCosto(ICargoDocente cargoDocente, float costo, int legajoDocente) {
        // Actualizar último costo:
        cargoDocente.setUltimoCosto(costo);

        // Instancia de docente:
        IDocente d = ctrlDocente.getIDocente();
        d.setLegajo(legajoDocente);

        // Persistir cambio:
        EstadoOperacion resultado = ctrlDocente.guardarCargoDocente(d, cargoDocente);
        switch (resultado.getEstado()) {
            case UPDATE_ERROR:
                throw new RuntimeException("Error de modificación de cargo docente: " + resultado.getMensaje());
            case UPDATE_OK:
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " + resultado.getMensaje());
        }
    }

    /**
     * Importa de una planilla de Excel los datos sobre las remuneraciones de los
     * docentes, y actualiza los costos de los cargos docentes correspondientes.
     * @param archivo Ruta del archivo de la planilla de cálculo.
     */
    public static void importar(String archivo) {
        List<List<String>> grilla = Excel.importar(archivo);

        // Saco los encabezados y las últimas filas con las fórmulas:
        grilla.remove(0);
        grilla.remove(0);
        grilla.remove(grilla.size() - 1);
        grilla.remove(grilla.size() - 1);

        for (List<String> filaGrilla : grilla) {
            try {
                int legajo = Integer.parseInt(filaGrilla.get(0).trim());

                int id = Integer.parseInt(filaGrilla.get(4).trim());
                ICargoDocente cargoDocente = buscarCargoDocente(id);

                float remConAporte = NumberFormat.getInstance().parse(filaGrilla.get(14)).floatValue();
                actualizarCosto(cargoDocente, remConAporte, legajo);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
