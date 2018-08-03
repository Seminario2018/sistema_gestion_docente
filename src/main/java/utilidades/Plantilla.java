package utilidades;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Clase que permite generar dinámicamente un mensaje a partir de una plantilla
 * @author LeoAM
 *
 */
public class Plantilla {

    /**
     * Arma un mensaje a partir de una plantilla y una serie de pares
     * clave/valor, que serán reemplazados en la plantilla.
     * @param archivo El nombre del archivo con la plantilla
     * @param valores Pares clave/valor a reemplazar en la plantilla
     * @return El mensaje armado
     */
    public static String armar(String archivo, Map<String, String> valores) {
        VelocityEngine vMotor = new VelocityEngine();
        vMotor.init();

        // Iniciar un contexto:
        VelocityContext vContexto = new VelocityContext();

        // Insertar los datos por clave:
        valores.forEach((k, v) -> vContexto.put(k, v));

        vContexto.put("tab", "\t");

        // Iniciar un escritor de Strings:
        Writer writer = new StringWriter();

        // Obtengo la plantilla de un archivo:
        Template plantilla = vMotor.getTemplate(archivo, "utf-8");
        plantilla.merge(vContexto, writer);

        // Obtengo el mensaje renderizado:
        return writer.toString();
    }

}
