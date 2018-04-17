package mail;

import modelo.cargo.ICargo;
import modelo.docente.IDocente;

/**
 * @author LeoAM
 * @version 1.0, 16 de abr. de 2018
 */
public interface IMail {
    public void enviarEmail(IDocente docente, ICargo cargo);
}
