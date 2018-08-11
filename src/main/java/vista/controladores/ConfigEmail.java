package vista.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ConfigEmail extends ControladorVista implements Initializable {

    private static final String ARCHIVO_CONFIG = "Mail.xml";
    private static final String ARCHIVO_PLANTILLA = "PlantillaNotificacion.txt";

    @FXML private CheckBox chkHabilitar;

    @FXML private void pruebaConfiguracion(ActionEvent event) {
        // TODO Prueba de configuración
    }

    @FXML
    private void aceptar(ActionEvent event) {
        // TODO Aceptar
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // TODO Cancelar
    }

    @FXML
    private void aplicar(ActionEvent event) {
        // TODO Aplicar
    }

    // Pestaña Servidor
    @FXML private TextField txtServidorEmail;
    @FXML private TextField txtServidorContraseña;
    @FXML private TextField txtServidorSmtp;
    @FXML private CheckBox chkServidorTLS;

    @FXML private void importarConfiguracion(ActionEvent event) {
        // TODO Importar configuración
    }

    @FXML
    private void exportarConfiguracion(ActionEvent event) {
        // TODO Exportar configuración
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
