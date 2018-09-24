package vista.controladores;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.w3c.dom.Document;
import controlador.ControlCargo;
import controlador.ControlDivision;
import controlador.ControlDocente;
import controlador.ControlPersona;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import mail.IMail;
import mail.Mail;
import mail.NotificacionCargo2;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoDocumento;
import modelo.division.IArea;
import modelo.division.IDivision;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IPersona;
import utilidades.Utilidades;


public class ConfigEmail extends ControladorVista implements Initializable {

    public static final String TITULO = "ConfigEmail";
    private static final String ARCHIVO_CONFIG = "Mail.xml";
    private static final String ARCHIVO_PLANTILLA = "Plantilla.xml";

    private static final List<String> LISTA_TAGS = Arrays.asList(
        "$DOCUMENTO",
        "$LEGAJO",
        "$APELLIDO",
        "$NOMBRE",
        "$DIVISION",
        "$AREA",
        "$CARGO",
        "$TIPOCARGO",
        "$ESTADO",
        "$DISPOSICION",
        "$DISPOSICIONDESDE",
        "$DISPOSICIONHASTA",
        "$RESOLUCION",
        "$RESOLUCIONDESDE",
        "$RESOLUCIONHASTA",
        "$ULTIMOCOSTO",
        "$ULTIMOCOSTOFECHA",
        "$APELLIDOJEFE");

    private Document configuracionXML;
    private Document plantillaXML;

    @Override
    public void inicializar() {
    	this.window.setTitle("Configuración de correo electrónico");
    }


    /**
     * Importa de un archivo seleccionado la configuración del
     * servidor de mail.
     * @param archivo Archivo a importar
     */
    private void importarArchivoConfig(File archivo) {
        configuracionXML = Utilidades.leerXML(archivo);

        String intervalo = configuracionXML.getElementsByTagName("intervalo").item(0).getTextContent();
        String email = configuracionXML.getElementsByTagName("usuario").item(0).getTextContent();
        String contraseña = configuracionXML.getElementsByTagName("contraseña").item(0).getTextContent();
        String smtp = configuracionXML.getElementsByTagName("puerto").item(0).getTextContent();
        String tls = configuracionXML.getElementsByTagName("tls").item(0).getTextContent();

        txtServidorIntervalo.setText(intervalo);
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
            actualizarConfiguracion();
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
        actualizarPlantilla();
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

        // Lleno el combobox con los tags:
        cmbPlantillaTag.setItems(FXCollections.observableArrayList(LISTA_TAGS));

        // Evento de click para los TextArea para saber cuál es sobre
        // el que se está trabajando:
        EventHandler<MouseEvent> clickTxtPlantilla = e -> {
            txtSeleccion = (TextArea) e.getSource();
        };

        txtPlantillaAsunto.setOnMouseClicked(clickTxtPlantilla);
        txtPlantillaEncabezado.setOnMouseClicked(clickTxtPlantilla);
        txtPlantillaMensaje.setOnMouseClicked(clickTxtPlantilla);
        txtPlantillaPie.setOnMouseClicked(clickTxtPlantilla);
    }

    // General:

    @FXML private CheckBox chkHabilitar;

    @FXML private void pruebaConfiguracion(ActionEvent event) {
        // Ingresar dirección de e-mail de destino:
        String direccionDestino = "";
        while (true) {
            try {
                direccionDestino = this.dialogoEntrada(TITULO, "Prueba de configuración", "Ingrese el mail a donde mandar la prueba", "@");
                if (direccionDestino == null) {
                    // "Cancelar" cancela la prueba:
                    throw new RuntimeException("<ConfigEmail> Operación cancelada");
                }

                // Validación de la dirección de correo:
                InternetAddress direccionInternet = new InternetAddress(direccionDestino);
                direccionInternet.validate();
                break;

            } catch (AddressException e) {
                System.out.println("<ConfigEmail> Destino: " + direccionDestino);
            }
        }

        ControlCargo controlCargo = new ControlCargo();
        ControlDivision controlDivision = new ControlDivision(this);
        ControlDocente controlDocente = new ControlDocente(this);
        ControlPersona controlPersona = new ControlPersona();

        EstadoOperacion estadoPrueba = new EstadoOperacion(CodigoEstado.INSERT_OK, "Prueba");

        // Persona prueba:
        IPersona personaPrueba = controlPersona.getIPersona();
        personaPrueba.setApellido("Lorem");
        personaPrueba.setNombre("Ipsum");
        personaPrueba.setTipoDocumento(new TipoDocumento(0, "DNI"));
        personaPrueba.setNroDocumento(12345678);

        // Docente prueba:
        IDocente docentePrueba = controlDocente.getIDocente();
        docentePrueba.setPersona(personaPrueba);
        docentePrueba.setLegajo(123456);
        EstadoDocente estadoDocentePrueba = new EstadoDocente();
        estadoDocentePrueba.setId(0);
        docentePrueba.setEstado(estadoDocentePrueba);

        // Cargo prueba:
        ICargoDocente cargoPrueba = controlDocente.getICargoDocente();
        IDivision divisionPrueba = controlDivision.listarDivisiones(null).get(0);
        IArea areaPrueba = controlDivision.getIArea();
        areaPrueba.setDivision(divisionPrueba);
        areaPrueba = controlDivision.listarAreas(areaPrueba).get(0);
        cargoPrueba.setArea(areaPrueba);
        cargoPrueba.setCargo(controlCargo.listarCargo(null).get(0));
        cargoPrueba.setEstado(new EstadoCargo(0, "Activo"));
        cargoPrueba.setTipoCargo(new TipoCargo(0, "Ordinario"));
        cargoPrueba.setDisposicion("Disposición ##/##");
        cargoPrueba.setDispDesde(LocalDate.now());
        cargoPrueba.setDispHasta(LocalDate.now());
        cargoPrueba.setResolucion("Resolución ##/##");
        cargoPrueba.setResDesde(LocalDate.now());
        cargoPrueba.setResHasta(LocalDate.now());
        cargoPrueba.setUltimoCosto(300000.0f);
        cargoPrueba.setFechaUltCost(LocalDate.now());

        Document plantillaXML = Utilidades.leerXML(new File("Plantilla.xml"));
        Map<String, String> parametros = NotificacionCargo2.armarParametrosPlantilla(docentePrueba, cargoPrueba, estadoPrueba);

        String asunto = NotificacionCargo2.armarTextoPlantilla("asunto", plantillaXML, parametros);
        String encabezado = NotificacionCargo2.armarTextoPlantilla("encabezado", plantillaXML, parametros);
        String mensaje = NotificacionCargo2.armarTextoPlantilla("mensaje", plantillaXML, parametros);

        IMail mail = new Mail();
        mail.enviarEmail(direccionDestino, asunto, encabezado + mensaje);
        System.out.println("<ConfigEmail> Mail enviado a: " + direccionDestino);
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
    @FXML private TextField txtServidorIntervalo;
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

    /**
     * Representa al último TextArea seleccionado por el usuario
     */
    private TextArea txtSeleccion = null;

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
            actualizarPlantilla();
            exportarArchivoPlantilla(archivo);
        }
    }

    @FXML
    private void insertarTag(ActionEvent event) {
        if (txtSeleccion != null) {
            // Ingresar el tag en la posición del cursor en el
            // TextArea seleccionado:
            txtSeleccion.insertText(
                txtSeleccion.getCaretPosition(),
                cmbPlantillaTag.getSelectionModel().getSelectedItem());
        }
    }


    // Pestaña Vista previa
    @FXML private WebView webVistaPrevia;

    @FXML private void mostrarVistaPrevia() {
        webVistaPrevia.getEngine().loadContent(
            txtPlantillaEncabezado.getText() +
            txtPlantillaMensaje.getText() +
            txtPlantillaPie.getText());
    }

    private void actualizarConfiguracion() {
        configuracionXML.getElementsByTagName("intervalo").item(0)
            .setTextContent(txtServidorIntervalo.getText());
        configuracionXML.getElementsByTagName("usuario").item(0)
            .setTextContent(txtServidorEmail.getText());
        configuracionXML.getElementsByTagName("contraseña").item(0)
            .setTextContent(txtServidorContraseña.getText());
        configuracionXML.getElementsByTagName("puerto").item(0)
            .setTextContent(txtServidorSmtp.getText());
        configuracionXML.getElementsByTagName("tls").item(0)
            .setTextContent(String.valueOf(chkServidorTLS.isSelected()));
    }

    private void actualizarPlantilla() {
        plantillaXML.getElementsByTagName("asunto").item(0)
            .setTextContent(txtPlantillaAsunto.getText());
        plantillaXML.getElementsByTagName("encabezado").item(0)
            .setTextContent(txtPlantillaEncabezado.getText());
        plantillaXML.getElementsByTagName("mensaje").item(0)
            .setTextContent(txtPlantillaMensaje.getText());
        plantillaXML.getElementsByTagName("pie").item(0)
            .setTextContent(txtPlantillaPie.getText());
    }
}
