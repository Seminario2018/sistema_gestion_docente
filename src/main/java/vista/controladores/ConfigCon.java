package vista.controladores;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistencia.Conexion;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de set. de 2018
 */
public class ConfigCon extends ControladorVista implements Initializable {

    public static final String TITULO = "Configuración de conexión";
    private static final String ARCHIVO_CONFIG = "Base.xml";

    private Document configuracionXML;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        importarArchivoConfig(new File(ARCHIVO_CONFIG));
    }

    @Override
    public void inicializar() {
        this.window.setTitle(TITULO);
    }

    @FXML private TextField txtDriver;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField pwdContrasena;
    @FXML private TextField txtURL;

    @FXML private Button btnProbar;
    @FXML void probarConexion(ActionEvent event) {
        Conexion con = new Conexion();
        try {
            Connection connection = con.conectar();
            if (connection == null) {
                dialogoInformacion(TITULO,
                    "Prueba de conexión",
                    "No pudo establecerse la conexión");
            } else {
                DatabaseMetaData metadata = connection.getMetaData();

                String info = "Database Product Name: " + metadata.getDatabaseProductName() + "\n" +
                    "Database Product Version: " + metadata.getDatabaseProductVersion() + "\n" +
                    "Driver Name: " + metadata.getDriverName() + "\n" +
                    "Driver Version: " + metadata.getDriverVersion() + "\n" +
                    "Max Connections: " + metadata.getMaxConnections() + "\n" +
                    "URL: " + metadata.getURL() + "\n" +
                    "User name: " + metadata.getUserName();

                dialogoInformacion(TITULO,
                    "Prueba de conexión",
                    "Conexión establecida\n\n" + info);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            alertaError(TITULO,
                "Prueba de Conexión",
                "Error de SQL durante la conexión");

        } finally {
            con.desconectar();
        }
    }

    @FXML private Button btnAceptar;
    @FXML void aceptar(ActionEvent event) {
        exportarArchivoConfig(new File(ARCHIVO_CONFIG));
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    @FXML private Button btnCancelar;
    @FXML void cancelar(ActionEvent event) {
        this.gestorPantalla.cerrarPantalla(TITULO);
    }

    /**
     * Importa de un archivo seleccionado la configuración de la
     * conexión a la base de datos.
     * @param archivo Archivo a importar
     */
    private void importarArchivoConfig(File archivo) {
        configuracionXML = Utilidades.leerXML(archivo);

        String driver = configuracionXML.getElementsByTagName("driver").item(0).getTextContent();
        String usr = configuracionXML.getElementsByTagName("usr").item(0).getTextContent();
        String password = configuracionXML.getElementsByTagName("password").item(0).getTextContent();
        String url = configuracionXML.getElementsByTagName("url").item(0).getTextContent();

        txtDriver.setText(driver);
        txtUsuario.setText(usr);
        pwdContrasena.setText(password);
        txtURL.setText(url);
    }

    /**
     * Exporta a un archivo seleccionado la configuración de la
     * conexión a la base de datos.
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
}