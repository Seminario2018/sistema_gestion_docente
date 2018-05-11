package mail;

import java.util.List;

import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IContacto;

public class NotificacionCargo {
    private static IMail mail = new Mail();

    private static String mailDesde = "semint2018@gmail.com";
    private static String contrasena = "semintunlu";

    public static void notificar(IDocente docente, ICargoDocente cargoDocente) {
        String asunto = "Actualización de cargo";
        String mensaje = String.format(
                "Se actualizó el siguiente cargo para el docente:\n"
                + "\tLegajo: %d\n"
                + "\tApellido: %s\n"
                + "\tNombre: %s\n"
                + "el siguiente cargo:\n"
                + "\tCargo: %s\n"
                + "\tÁrea: %s\n"
                + "\tDivisión: %d\n"
                + "\tTipo de cargo: %s\n"
                + "\tEstado de cargo: %s\n",
                docente.getLegajo(),
                docente.getPersona().getApellido(),
                docente.getPersona().getNombre(),
                cargoDocente.getCargo().getDescripcion(),
                cargoDocente.getArea().getCodigo(),
                cargoDocente.getArea().getDivision().getCodigo(),
                cargoDocente.getTipoCargo().getDescripcion(),
                cargoDocente.getEstado().getDescripcion()
        );

        List<IContacto> contactosJefe = cargoDocente
                .getArea()
                .getDivision()
                .getJefe()
                .getPersona()
                .getContactos();

        // Se envía un mail a cada contacto del tipo MailLaboral del jefe:
        StringBuilder destinos = new StringBuilder();
        for (IContacto contacto : contactosJefe) {
            if (contacto.getTipo().getDescripcion().equals("MailLaboral")) {
                destinos.append(contacto.getDato())
                        .append(",");
            }
        }
        destinos.setLength(destinos.length() - 1);

        mail.enviarEmail(mailDesde, destinos.toString(), asunto, mensaje, contrasena);
    }
}
