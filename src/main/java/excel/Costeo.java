package excel;

import java.util.List;

import modelo.cargo.Cargo;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.docente.CargoDocente;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.persona.Persona;

public class Costeo {

    private static GestorCargo gestorCargo = new GestorCargo();
    private static GestorDocente gestorDocente = new GestorDocente();
    private static GestorPersona gestorPersona = new GestorPersona();

    public static void importar(String archivo)
            throws UnsupportedOperationException {

        List<List<String>> grilla = Excel.importar(archivo);

        // Saco los encabezados:
        grilla.remove(0);
        grilla.remove(0);

        // Saco las últimas filas con las fórmulas:
        grilla.remove(grilla.size() - 1);
        grilla.remove(grilla.size() - 1);

        for (List<String> fila : grilla) {
            int legajo = Integer.parseInt(fila.get(0));
            String apellido = fila.get(1);
            String nombre = fila.get(2);
//            int idCategoria = Integer.parseInt(fila.get(3));
            int codigoCargo = Integer.parseInt(fila.get(4));
//            String esc = fila.get(5);
//            int fuente = Integer.parseInt(fila.get(6));
//            int prog = Integer.parseInt(fila.get(7));
//            int subProg = Integer.parseInt(fila.get(8));
//            int proy = Integer.parseInt(fila.get(9));
//            int act = Integer.parseInt(fila.get(10));
//            int obra = Integer.parseInt(fila.get(11));
//            int fin = Integer.parseInt(fila.get(12));
//            int func = Integer.parseInt(fila.get(13));
//            int remConAporte = Integer.parseInt(fila.get(14));
//            int remSinAporte = Integer.parseInt(fila.get(15));
//            int descuento = Integer.parseInt(fila.get(16));
//            int neto = Integer.parseInt(fila.get(17));
//            int jubilacion = Integer.parseInt(fila.get(18));
//            int ley = Integer.parseInt(fila.get(19));
//            int obraSocial = Integer.parseInt(fila.get(20));
//            int ansal = Integer.parseInt(fila.get(21));
//            int art = Integer.parseInt(fila.get(22));
//            int seguro = Integer.parseInt(fila.get(23));

            // DONE Cargar persona
            List<IPersona> listaPersonas = gestorPersona.listarPersonas(
                    new Persona(apellido, nombre, null, null, 0, null, null, null, null));
            if (listaPersonas.size() == 1) {
                IPersona persona = listaPersonas.get(0);

                // DONE Cargar docente
                IDocente docente = gestorDocente.listarDocente(
                        new Docente(persona, legajo, "", null, null, null, null))
                        .get(0);

                // DONE Cargar cargoDocente
                ICargo cargo = gestorCargo.listarCargo(
                        new Cargo(codigoCargo, null, 0))
                        .get(0);

                ICargoDocente cd = new CargoDocente();
                cd.setCargo(cargo);
                ICargoDocente cargoDocente = gestorDocente
                        .listarCargo(docente, cd)
                        .get(0);

            } else {
                throw new UnsupportedOperationException(
                        "Más de una persona con el mismo nombre y apellido para una fila en planilla.");
            }
            // TODO Hacer (?)
        }
    }
}
