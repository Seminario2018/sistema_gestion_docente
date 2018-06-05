package utilidades;

/**
 * Clase que logea las consultas realizadas a la BD.
 * @author LeoAM
 *
 */
public class LogQuery {

    /**
     * Loggea la consulta. Puede ser modificada para ignorar los mensaje si se
     * comenta la línea, por ejemplo.
     * @param query La consulta realizada
     */
    public static void log(String query) {
        System.out.println(query);
    }

}
