package mail;

import java.util.List;

import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.Contacto;
import modelo.persona.IContacto;

public class NotificacionCargo {
    private static IMail mail = new Mail();

    public static void notificar(IDocente docente, ICargoDocente cargoDocente) {
        String asunto = "Actualización de cargo";
        String mensaje = String.format(
                "Se actualizó el siguiente cargo para el docente:"
                + "\tLegajo: %d\n"
                + "\tApellido: %s\n"
                + "\tNombre: %s\n"
                + "el siguiente cargo:\n"
                + "\tCargo: %s\n"
                + "\tÁrea: %d\n"
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

        List<Contacto> contactosJefe = cargoDocente
                .getArea()
                .getDivision()
                .getJefe()
                .getPersona()
                .getContactos();

        // Se envía un mail a cada contacto del tipo MailLaboral del jefe:
        StringBuilder destinos = new StringBuilder();
        for (IContacto contacto : contactosJefe) {
            if (contacto.getTipo() == "MailLaboral") {
                destinos.append(contacto.getValor())
                        .append(",");
            }
        }
        destinos.setLength(destinos.length() - 1);

        mail.enviarEmail(destinos.toString(), asunto, mensaje);
    }
}
