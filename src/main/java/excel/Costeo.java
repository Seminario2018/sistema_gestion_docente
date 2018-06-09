package excel;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import controlador.ControlCargo;
import controlador.ControlDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.ICargo;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IPersona;

/**
 * Clase que importa el costeo de una planilla Excel.
 * @author LeoAM
 */
public class Costeo {

    private static ControlCargo ctrlCargo = new ControlCargo();
    private static ControlDocente ctrlDocente = new ControlDocente(null);

    /**
     * Clase que analiza cada fila de la planilla de costos
     * @author LeoAM
     *
     */
    public static class FilaCosteo {

        private List<String> errores = new ArrayList<String>();

        private String strlegajoDocente;
        private String apellido;
        private String nombre;
        private String strCodigoCargo;
        private String strIdCargoDocente;
        private String strRemConAporte;

        private float remConAporte;
        private ICargoDocente cargoDocente;
        private IDocente docente;

        public FilaCosteo(List<String> fila) {
            this.strlegajoDocente = fila.get(0).trim();
            this.apellido = fila.get(1).trim();
            this.nombre = fila.get(2).trim();
            this.strCodigoCargo = fila.get(3).trim();
            this.strIdCargoDocente = fila.get(4).trim();
            this.strRemConAporte = fila.get(14).trim();

            comprobarDocente();
            comprobarCargoDocente();
            comprobarRemuneracion();
        }

        private void comprobarCargoDocente() {
            try {
                int idCargoDocente = Integer.parseInt(this.strIdCargoDocente);

                ICargoDocente cargoDocenteBusqueda = ctrlDocente.getICargoDocente();
                cargoDocenteBusqueda.setId(idCargoDocente);
                List<ICargoDocente> listaCargosDocentes = ctrlDocente.listarCargosDocente(null, cargoDocenteBusqueda);
                if (listaCargosDocentes.isEmpty()) {
                    errores.add("El cargo no existe");
                } else {
                    this.cargoDocente = listaCargosDocentes.get(0);
                    this.comprobarCargo(cargoDocente.getCargo());
                }

            } catch (NumberFormatException e) {
                errores.add("El cargo no es numérico.");
            }
        }

        private void comprobarCargo(ICargo cargo) {
            try {
                int codigoCargo = Integer.parseInt(this.strCodigoCargo);

                ICargo cargoBusqueda = ctrlCargo.getICargo();
                cargoBusqueda.setCodigo(codigoCargo);
                List<ICargo> listaCargos = ctrlCargo.listarCargo(cargoBusqueda);
                if (listaCargos.isEmpty()) {
                    // No existe ningún cargo con ese código
                    errores.add("No existe esa categoría de cargo");
                } else {
                    if (cargo.getCodigo() != listaCargos.get(0).getCodigo()) {
                        errores.add("No coincide la categoría del cargo docente");
                    }
                }

            } catch (NumberFormatException e) {
                errores.add("La categoría no es numérica.");
            }
        }

        private void comprobarDocente() {
            try {
                int legajoDocente = Integer.parseInt(this.strlegajoDocente);
                IDocente docenteBusqueda = ctrlDocente.getIDocente();
                docenteBusqueda.setLegajo(legajoDocente);
                List<IDocente> listaDocentes = ctrlDocente.listarDocente(docenteBusqueda);
                if (listaDocentes.isEmpty()) {
                    errores.add("El docente con ese legajo no existe");
                } else {
                    this.docente = listaDocentes.get(0);
                    this.comprobarPersona();
                }
            } catch (NumberFormatException e) {
                errores.add("El legajo no es numérico");
            }
        }

        private void comprobarPersona() {
            IPersona persona = this.docente.getPersona();
            if (persona == null) {
                errores.add("El docente no tiene datos personales asociados");
            } else {
                if (!persona.getApellido().equals(this.apellido)) {
                    errores.add("El apellido no coincide");
                }
                if (!persona.getNombre().equals(this.nombre)) {
                    errores.add("El nombre no coincide");
                }
            }
        }

        private void comprobarRemuneracion() {
            try {
                this.remConAporte = Float.parseFloat(this.strRemConAporte);
            } catch (NumberFormatException e) {
                errores.add("La remuneración con aporte debe ser numérico");
            }
        }

        public String getErrores() {
            return String.join("\n", this.errores);
        }

        public EstadoOperacion actualizarCosto() {
            this.cargoDocente.setUltimoCosto(this.remConAporte);
            this.cargoDocente.setFechaUltCost(LocalDate.now());
            return ctrlDocente.guardarCargoDocente(this.docente, this.cargoDocente);
        }
    }

    /**
     * Importa de una planilla de Excel los datos sobre las remuneraciones de los
     * docentes, y actualiza los costos de los cargos docentes correspondientes.
     * @param archivo Ruta del archivo de la planilla de cálculo.
     * @throws IOException
     * @throws InvalidFormatException
     * @throws EncryptedDocumentException
     */
    public static void importar(File archivo) throws EncryptedDocumentException, InvalidFormatException, IOException {
        List<List<String>> grilla = Excel.importar(archivo);

        // Saco los encabezados y las últimas filas con las fórmulas:
        grilla.remove(0);
        grilla.remove(0);
        grilla.remove(grilla.size() - 1);
        grilla.remove(grilla.size() - 1);

        int fila = 3;
        for (List<String> filaGrilla : grilla) {
            FilaCosteo filaCosteo = new FilaCosteo(filaGrilla);

            // TODO Mostrar en vista
            System.out.printf("Fila: %s\n", fila++);
            String errores = filaCosteo.getErrores();
            if (errores.equals("")) {
//                filaCosteo.actualizarCosto();
            } else {
                System.out.println(errores);
            }
        }
    }
}
