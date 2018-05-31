package excel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import controlador.ControlCargo;
import controlador.ControlDocente;
import controlador.ControlPersona;
import modelo.cargo.ICargo;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IPersona;

@SuppressWarnings("unused")
public class Costeo {

    /**
     * Clase que analiza una fila de la planilla de costeo.
     *
     * @author LeoAM
     *
     */
    private static class FilaCosteo {

        // Columnas:
        private int legajo;
        private String apellido;
        private String nombre;
        private int categoria;
        private int cargo;
        private String esc;
        private int fuente;
        private int prog;
        private int subProg;
        private int proy;
        private int act;
        private int obra;
        private int fin;
        private int func;
        private float remConAporte;
        private float remSinAporte;
        private float descuento;
        private float neto;
        private float jubilacion;
        private float ley;
        private float obraSocial;
        private float ansal;
        private float art;
        private float seguro;

        private IPersona persona;
        private IDocente docente;
        private ICargoDocente cargoDocente;
        private float costo;

        public FilaCosteo(List<String> filaGrilla) throws ParseException {
            // Inicializar formateador de decimales:
            NumberFormat nf = NumberFormat.getInstance();

            // Sacar los datos de las celdas de la fila:
            this.legajo = Integer.parseInt(filaGrilla.get(0).trim());
            this.apellido = filaGrilla.get(1).trim();
            this.nombre = filaGrilla.get(2).trim();
            this.categoria = Integer.parseInt(filaGrilla.get(3).trim());
            this.cargo = Integer.parseInt(filaGrilla.get(4).trim());
            this.esc = filaGrilla.get(5).trim();
            this.fuente = Integer.parseInt(filaGrilla.get(6).trim());
            this.prog = Integer.parseInt(filaGrilla.get(7).trim());
            this.subProg = Integer.parseInt(filaGrilla.get(8).trim());
            this.proy = Integer.parseInt(filaGrilla.get(9).trim());
            this.act = Integer.parseInt(filaGrilla.get(10).trim());
            this.obra = Integer.parseInt(filaGrilla.get(11).trim());
            this.fin = Integer.parseInt(filaGrilla.get(12).trim());
            this.func = Integer.parseInt(filaGrilla.get(13).trim());

            this.remConAporte = nf.parse(filaGrilla.get(14)).floatValue();
            this.remSinAporte = nf.parse(filaGrilla.get(15)).floatValue();
            this.descuento = nf.parse(filaGrilla.get(16)).floatValue();
            this.neto = nf.parse(filaGrilla.get(17)).floatValue();
            this.jubilacion = nf.parse(filaGrilla.get(18)).floatValue();
            this.ley = nf.parse(filaGrilla.get(19)).floatValue();
            this.obraSocial = nf.parse(filaGrilla.get(20)).floatValue();
            this.ansal = nf.parse(filaGrilla.get(21)).floatValue();
            this.art = nf.parse(filaGrilla.get(22)).floatValue();
            this.seguro = nf.parse(filaGrilla.get(23)).floatValue();

            crearPersona();
            crearDocente();
            crearCargoDocente();
            calcularCosto();
        }

        private void crearPersona() {
            ControlPersona ctrlPersona = new ControlPersona();

            IPersona personaBusqueda = ctrlPersona.getIPersona();
            personaBusqueda.setApellido(this.apellido);
            personaBusqueda.setNombre(this.nombre);

            List<IPersona> listaPersonas = ctrlPersona.listarPersonas(personaBusqueda);
            if (listaPersonas.size() == 1) {
                this.persona = listaPersonas.get(0);
            } else {
                throw new UnsupportedOperationException("Más de una o ninguna persona con el nombre y apellido para una fila en planilla.");
            }
        }

        private void crearDocente() {
            ControlDocente ctrlDocente = new ControlDocente(null);
            IDocente docenteBusqueda = ctrlDocente.getIDocente();
            docenteBusqueda.setLegajo(this.legajo);

            List<IDocente> listaDocentes = ctrlDocente.listarDocente(docenteBusqueda);
            this.docente = listaDocentes.get(0);
        }

        private void crearCargoDocente() {
            ControlCargo ctrlCargo = new ControlCargo();
            ControlDocente ctrlDocente = new ControlDocente(null);

            ICargo cargoBusqueda = ctrlCargo.getICargo();
            cargoBusqueda.setCodigo(this.categoria);
            ICargo cargo = ctrlCargo.listarCargo(cargoBusqueda).get(0);

            ICargoDocente cargoDocenteBusqueda = ctrlDocente.getICargoDocente();
            cargoDocenteBusqueda.setId(this.cargo);
            cargoDocenteBusqueda.setCargo(cargo);
            this.cargoDocente = ctrlDocente.listarCargosDocente(this.docente, cargoDocenteBusqueda).get(0);
        }

        private void calcularCosto() {
            assert(this.cargoDocente != null) : "cargoDocente null";
            float costo =
                remConAporte
                + remSinAporte
                - descuento
                + neto
                + jubilacion
                + ley
                + obraSocial
                + ansal
                + art
                + seguro;
            this.cargoDocente.setUltimoCosto(costo);
        }

        public IPersona getPersona() {
            return this.persona;
        }

        public IDocente getDocente() {
            return this.docente;
        }

        public ICargoDocente getCargoDocente() {
            return this.cargoDocente;
        }
    }

    public static void importar(String archivo) throws UnsupportedOperationException, ParseException {
        List<List<String>> grilla = Excel.importar(archivo);

        // Saco los encabezados:
        grilla.remove(0);
        grilla.remove(0);

        // Saco las últimas filas con las fórmulas:
        grilla.remove(grilla.size() - 1);
        grilla.remove(grilla.size() - 1);

        for (List<String> filaGrilla : grilla) {
            FilaCosteo filaCosteo = new FilaCosteo(filaGrilla);
            // TODO Hacer (?)

            /* TEST Fila costeo */
            System.out.printf("%d\t%f\n", filaCosteo.getDocente().getLegajo(), filaCosteo.getCargoDocente().getUltimoCosto());
            //*/
        }
    }
}
