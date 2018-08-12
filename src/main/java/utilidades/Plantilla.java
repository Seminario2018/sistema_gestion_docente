package utilidades;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Clase que permite generar dinámicamente un mensaje a partir de una plantilla
 * @author LeoAM
 *
 */
public class Plantilla {

    /**
     * Arma un mensaje a partir de una plantilla y una serie de pares
     * clave/valor, que serán reemplazados en la plantilla.
     * @param plantilla El string con la plantilla
     * @param valores Pares clave/valor a reemplazar en la plantilla
     * @return El mensaje armado
     */
    public static String armar(String plantilla, Map<String, String> valores) {
        // Iniciar un contexto:
        VelocityContext vContexto = new VelocityContext();

        // Insertar los datos por clave:
        valores.forEach((k, v) -> vContexto.put(k, v));

        // Iniciar un escritor de Strings:
        Writer writer = new StringWriter();

        Velocity.evaluate(vContexto, writer, "", plantilla);

        // Obtengo el mensaje renderizado:
        return writer.toString();
    }

}
