package mail;

import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IContacto;

public class NotificacionCargo {

    private static IMail mail = new Mail();

    private static String mailDesde = "semint2018@gmail.com";
    private static String contrasena = "semintunlu";

    private ICargoDocente cargoDocente;
    private IDocente docente;

    /**
     * Inicializa los objetos de cargodocente necesarios para armar el mensaje
     * de la notificación. <br>
     * Los objetos inicializados son:
     * <ul>
     *     <li>area (luego se obtiene la división)</li>
     *     <li>cargo</li>
     *     <li>tipo de cargo</li>
     *     <li>estado de cargo</li>
     * </ul>
     */
    private void cargoDocenteObtenerReferencias() {
        this.cargoDocente.getArea();
        this.cargoDocente.getCargo();
        this.cargoDocente.getTipoCargo();
        this.cargoDocente.getEstado();
    }

    /**
     * Inicia un objeto de notificación, guardando una referencia al docente y
     * al cargo docente a notificar.
     * @param docente Docente del cargo
     * @param cargoDocente Cargo
     */
    public NotificacionCargo(IDocente docente, ICargoDocente cargoDocente) {
        this.cargoDocente = cargoDocente;
        this.docente = docente;

        cargoDocenteObtenerReferencias();
    }

    /** Arma el asunto del mensaje de acuerdo al resultado de la operación */
    private static String armarAsunto(EstadoOperacion estadoOperacion) {
        String asunto;
        switch (estadoOperacion.getEstado()) {
            case INSERT_OK:
                asunto = "Adición";
                break;
            case UPDATE_OK:
                asunto = "Actualización";
                break;
            case DELETE_OK:
                asunto = "Quita";
                break;
            default:
                throw new UnsupportedOperationException(
                    "EstadoOperacion no soportado");
        }
        return asunto + " de cargo";
    }

    /** Devuelve la operación que se realizó de acuerdo al resultado de la
     * operación */
    private static String armarOperacionMensaje(EstadoOperacion estadoOperacion) {
        switch (estadoOperacion.getEstado()) {
            case INSERT_OK:
                return "agregó";
            case UPDATE_OK:
                return "actualizó";
            case DELETE_OK:
                return "quitó";
            default:
                throw new UnsupportedOperationException("EstadoOperacion no soportado");
        }
    }

    /**
     * Notifica por mail el agregado, modificación o eliminación del cargo
     * docente, según indicado en {@code NotificacionCargo(IDocente,
     * ICargoDocente)}.
     * @param estadoOperacion Resultado de la operación.
     */
    public void notificar(EstadoOperacion estadoOperacion) {
        String asunto = armarAsunto(estadoOperacion);
        String operacionMensaje = armarOperacionMensaje(estadoOperacion);

        String mensaje = "Al docente:\n\n"
            + "\tLegajo: " + docente.getLegajo()+ "\n"
            + "\tApellido: " + docente.getPersona().getApellido() + "\n"
            + "\tNombre: " + docente.getPersona().getNombre() + "\n"
            + "\nSe le " + operacionMensaje + " el siguiente cargo:\n\n"
            + "\tCargo: " + cargoDocente.getCargo().getDescripcion() + "\n"
            + "\tÁrea: " + cargoDocente.getArea().getDescripcion() + "\n"
            + "\tDivisión: " + cargoDocente.getArea().getDivision().getDescripcion() + "\n"
            + "\tTipo de cargo: " + cargoDocente.getTipoCargo().getDescripcion() + "\n"
            + "\tEstado de cargo: " + cargoDocente.getEstado().getDescripcion() + "\n";

        // Obtengo todos los contactos del jefe de la división:
        List<IContacto> contactosJefe = cargoDocente.getArea().getDivision().getJefe().getPersona().getContactos();
        if (contactosJefe.isEmpty()) {
            throw new RuntimeException("El jefe de división no tiene contactos!");
        }

        // Se envía un mail a cada contacto del tipo MailLaboral del jefe:
        StringBuilder destinos = new StringBuilder();
        for (IContacto contacto : contactosJefe) {
            if (contacto.getTipo().getDescripcion().equals("Mail Laboral")) {
                destinos.append(contacto.getDato()).append(",");
            }
        }
        destinos.setLength(destinos.length() - 1);

        mail.enviarEmail(destinos.toString(), asunto, mensaje);
    }
}
