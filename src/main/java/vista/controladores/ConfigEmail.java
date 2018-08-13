package vista.controladores;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
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
    private static final String ARCHIVO_PLANTILLA = "Plantilla.xml";

    private Document configuracionXML;
    private Document plantillaXML;

    /**
     * Importa de un archivo seleccionado la configuración del
     * servidor de mail.
     * @param archivo Archivo a importar
     */
    private void importarArchivoConfig(File archivo) {
        configuracionXML = Utilidades.leerXML(archivo);

        String email = configuracionXML.getElementsByTagName("usuario").item(0).getTextContent();
        String contraseña = configuracionXML.getElementsByTagName("contraseña").item(0).getTextContent();
        String smtp = configuracionXML.getElementsByTagName("puerto").item(0).getTextContent();
        String tls = configuracionXML.getElementsByTagName("tls").item(0).getTextContent();

        txtServidorEmail.setText(email);
        txtServidorContraseña.setText(contraseña);
        txtServidorSmtp.setText(smtp);
        chkServidorTLS.setSelected(Boolean.valueOf(tls));
    }

    /**
     * Exporta a un archivo seleccionado la configuración del
     * servidor de mail.
     * @param archivo Archivo a donde exportar
     */
    private void exportarArchivoConfig(File archivo) {
        try {
            Utilidades.guardarXML(archivo, configuracionXML);
        } catch (Exception e) {
            e.printStackTrace();
            alertaError(TITULO, "No se pudo guardar el archivo", "No se pudo guardar el archivo de configuración");
        }
    }

    /**
     * Importa de un archivo seleccionado la plantilla del mail de
     * notificación de cargo.
     * Recorta los espacios en blanco adelante y atrás del contenido
     * de cada parte de la plantilla en el XML.
     * @param archivo Archivo a importar
     */
    private void importarArchivoPlantilla(File archivo) {
        plantillaXML = Utilidades.leerXML(archivo);

        String asunto = plantillaXML.getElementsByTagName("asunto").item(0).getTextContent().trim();
        String encabezado = plantillaXML.getElementsByTagName("encabezado").item(0).getTextContent().trim();
        String mensaje = plantillaXML.getElementsByTagName("mensaje").item(0).getTextContent().trim();
        String pie = plantillaXML.getElementsByTagName("pie").item(0).getTextContent().trim();

        txtPlantillaAsunto.setText(asunto);
        txtPlantillaEncabezado.setText(encabezado);
        txtPlantillaMensaje.setText(mensaje);
        txtPlantillaPie.setText(pie);
    }

    /**
     * Exporta a un archivo seleccionado la plantilla del mail.
     * @param archivo Archivo a donde exportar
     */
    private void exportarArchivoPlantilla(File archivo) {
        plantillaXML.getElementsByTagName("asunto").item(0).setTextContent(txtPlantillaAsunto.getText());
        plantillaXML.getElementsByTagName("encabezado").item(0).setTextContent(txtPlantillaEncabezado.getText());
        plantillaXML.getElementsByTagName("mensaje").item(0).setTextContent(txtPlantillaMensaje.getText());
        plantillaXML.getElementsByTagName("pie").item(0).setTextContent(txtPlantillaPie.getText());

        try {
            Utilidades.guardarXML(archivo, plantillaXML);
        } catch (Exception e) {
            e.printStackTrace();
            alertaError(TITULO, "No se pudo guardar el archivo", "No se pudo guardar el archivo de configuración");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        importarArchivoConfig(new File(ARCHIVO_CONFIG));
        importarArchivoPlantilla(new File(ARCHIVO_PLANTILLA));
    }

    // General:

    @FXML private CheckBox chkHabilitar;

    @FXML private void pruebaConfiguracion(ActionEvent event) {
        // TODO Prueba de configuración
    }

    @FXML
    private void aceptar(ActionEvent event) {
        exportarArchivoConfig(new File(ARCHIVO_CONFIG));
        exportarArchivoPlantilla(new File(ARCHIVO_PLANTILLA));
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    @FXML
    private void aplicar(ActionEvent event) {
        exportarArchivoConfig(new File(ARCHIVO_CONFIG));
        exportarArchivoPlantilla(new File(ARCHIVO_PLANTILLA));
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
        File archivo = this.elegirArchivo("Elegir archivo de plantilla", "Archivos XML", Arrays.asList("*.xml"));
        if (archivo != null) {
            importarArchivoPlantilla(archivo);
        }
    }

    @FXML
    private void exportarPlantilla(ActionEvent event) {
        File archivo = this.elegirArchivo("Elegir archivo de plantilla", "Archivos XML", Arrays.asList("*.xml"));
        if (archivo != null) {
            exportarArchivoPlantilla(archivo);
        }
    }

    @FXML
    private void insertarTag(ActionEvent event) {
        // TODO Insertar tag
    }


    // Pestaña Vista previa
    @FXML private TextArea txtVistaprevia;

}
