package vista.controladores;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utilidades.Utilidades;


public class ConfigEmail extends ControladorVista implements Initializable {

    private static final String TITULO = "Configuración E-mail";
    private static final String ARCHIVO_CONFIG = "Mail.xml";
    private static final String ARCHIVO_PLANTILLA = "PlantillaNotificacion.txt";

    private Document configuracionXML;
    private Document plantillaXML;

    /**
     * Importa de un archivo seleccionado la configuración del
     * servidor de mail.
     * @param archivo Nombre del archivo a importar
     */
    private void importarArchivoConfig(File archivo) {
        configuracionXML = Utilidades.leerXML(archivo);

        String email = configuracionXML.getElementsByTagName("usuario").item(0).getTextContent();
        String contraseña = configuracionXML.getElementsByTagName("contraseña").item(0).getTextContent();
        String smtp = configuracionXML.getElementsByTagName("puerto").item(0).getTextContent();
        String tls = configuracionXML.getElementsByTagName("tls").item(0).getTextContent();

        System.out.println("=================================================");
        System.out.println("Importando de " + archivo + ":");
        System.out.println("    > Email:       " + email);
        System.out.println("    > Contraseña:  " + contraseña);
        System.out.println("    > Puerto SMTP: " + smtp);
        System.out.println("    > TLS:         " + tls);
        System.out.println("=================================================");

        txtServidorEmail.setText(email);
        txtServidorContraseña.setText(contraseña);
        txtServidorSmtp.setText(smtp);
        chkServidorTLS.setSelected(Boolean.valueOf(tls));
    }

    /**
     *
     * @param archivo
     */
    private void exportarArchivoConfig(File archivo) {
        try {
            Utilidades.guardarXML(archivo, configuracionXML);
        } catch (TransformerException e) {
            e.printStackTrace();
            alertaError(TITULO, "No se pudo guardar el archivo", "No se pudo guardar el archivo de configuración");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        importarArchivoConfig(new File(ARCHIVO_CONFIG));
    }

    // General:

    @FXML private CheckBox chkHabilitar;

    @FXML private void pruebaConfiguracion(ActionEvent event) {
        // TODO Prueba de configuración
    }

    @FXML
    private void aceptar(ActionEvent event) {
        // TODO Aceptar
        exportarArchivoConfig(new File(ARCHIVO_CONFIG));
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    @FXML
    private void aplicar(ActionEvent event) {
        // TODO Aplicar
        exportarArchivoConfig(new File(ARCHIVO_CONFIG));
    }

    // Pestaña Servidor
    @FXML private TextField txtServidorEmail;
    @FXML private TextField txtServidorContraseña;
    @FXML private TextField txtServidorSmtp;
    @FXML private CheckBox chkServidorTLS;

    @FXML private void importarConfiguracion(ActionEvent event) {
        File archivo = this.elegirArchivo("Elegir archivo de configuración", "Archivos XML", Arrays.asList("*.xml"));
        if (archivo != null) {
            importarArchivoConfig(archivo);
        }
    }

    @FXML
    private void exportarConfiguracion(ActionEvent event) {
        File archivo = this.elegirArchivo("Elegir archivo de configuración", "Archivos XML", Arrays.asList("*.xml"));
        if (archivo != null) {
            exportarArchivoConfig(archivo);
        }
    }

    // Pestaña Plantilla
    @FXML private ComboBox<String> cmbPlantillaTag;
    @FXML private TextArea txtPlantillaAsunto;
    @FXML private TextArea txtPlantillaEncabezado;
    @FXML private TextArea txtPlantillaMensaje;
    @FXML private TextArea txtPlantillaPie;

    @FXML
    private void importarPlantilla(ActionEvent event) {
        // TODO Importar plantilla
    }

    @FXML
    private void exportarPlantilla(ActionEvent event) {
        // TODO Exportar plantilla
    }

    @FXML
    private void insertarTag(ActionEvent event) {
        // TODO Insertar tag
    }


    // Pestaña Vista previa
    @FXML private TextArea txtVistaprevia;

}
