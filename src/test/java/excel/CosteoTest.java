package excel;

import java.io.File;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import modelo.costeo.Costeo;


public class CosteoTest {

    File archivoPlanilla;
    String rutaArchivo = "src/test/resources/costeo_febrero.xls";

    @Before
    public void inicializar() {
        archivoPlanilla = new File(rutaArchivo);
    }

    @Test
    public void test() {
        try {
            Costeo.importar(archivoPlanilla);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncryptedDocumentException e) {
            System.out.println("El archivo está protegido por contraseña");
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

}
