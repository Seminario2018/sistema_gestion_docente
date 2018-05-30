package vista.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controlador.ControlPersona;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.IPersona;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Personas extends ControladorVista implements Initializable {

    private static final String TITULO = "Personas";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

// -------------------------------- General --------------------------------- //

	private IPersona personaSeleccionada = null;
	private ControlPersona controlPersona = new ControlPersona();

	/** Muestra los datos de la persona en los controles generales */
	private void generalMostrarPersona() {
	    if (personaSeleccionada != null) {
	        txtPersonasDocumento.setText(String.valueOf(personaSeleccionada.getNroDocumento()));
	        txtPersonasNombre.setText(personaSeleccionada.getNombreCompleto());
	    }
	}

	/** Vacía los controles generales */
	private void generalVaciarCampos() {
	    txtPersonasDocumento.clear();
	    txtPersonasNombre.clear();
	}

	@FXML private TextField txtPersonasDocumento;
	@FXML private TextField txtPersonasNombre;

	@FXML private Button btnPersonasBuscar;
	@FXML public void buscarPersona(ActionEvent event) {
	    /* TEST General: Selección de persona */
        // Recuperar la persona con DNI = 10071374
        IPersona personaBusqueda = this.controlPersona.getIPersona();
        personaBusqueda.setNroDocumento(10071374);
        List<IPersona> listaPersonas = this.controlPersona.listarPersonas(personaBusqueda);
        personaSeleccionada = listaPersonas.get(0);
        //*/
        generalMostrarPersona();
        datosMostrarPersona();
	}

	@FXML private Button btnPersonasNueva;
	@FXML public void nuevaPersona(ActionEvent event) {

	}

	@FXML private Button btnPersonasEliminar;
	@FXML public void eliminarPersona(ActionEvent event) {
	    EstadoOperacion resultado = this.controlPersona.eliminarPersona(personaSeleccionada);

	    switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Persona", resultado.getMensaje());
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Persona", resultado.getMensaje());
                personaSeleccionada = null;

                generalVaciarCampos();
                datosVaciarCampos();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
	    }
	}

// ----------------------------- Pestaña Datos ------------------------------ //

	@FXML private void inicializarDatos() {
	    cmbDatosTipoDocumento.setItems(
	        FXCollections.observableArrayList(
	            TipoDocumento.getLista()));
	}

	/** Muestra los datos de la persona seleccionada: */
	private void datosMostrarPersona() {
	    if (personaSeleccionada != null) {
	        txtDatosApellido.setText(personaSeleccionada.getApellido());
	        txtDatosNombre.setText(personaSeleccionada.getNombre());
	        cmbDatosTipoDocumento.getSelectionModel().select(personaSeleccionada.getTipoDocumento());
	        txtDatosDocumento.setText(String.valueOf(personaSeleccionada.getNroDocumento()));
	        dtpDatosFechaNacimiento.setValue(personaSeleccionada.getFechaNacimiento());
	    }
	}

	/** Vacía los campos de datos */
	private void datosVaciarCampos() {
	    txtDatosApellido.clear();
	    txtDatosNombre.clear();
	    cmbDatosTipoDocumento.getSelectionModel().clearSelection();
	    txtDatosDocumento.clear();
	    dtpDatosFechaNacimiento.setValue(null);
	}

	@FXML private Button btnDatosGuardar;
	@FXML public void guardarPersona(ActionEvent event) {
	    if (personaSeleccionada != null) {
	        try {
	            personaSeleccionada.setNroDocumento(Integer.parseInt(txtDatosDocumento.getText()));
    	        personaSeleccionada.setApellido(txtDatosApellido.getText());
    	        personaSeleccionada.setNombre(txtDatosNombre.getText());
    	        personaSeleccionada.setTipoDocumento(cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
    	        personaSeleccionada.setFechaNacimiento(dtpDatosFechaNacimiento.getValue());

    	        this.controlPersona.modificarPersona(personaSeleccionada);
	        } catch (NumberFormatException nfe) {
	            alertaError(TITULO, "Guardar Persona", "El documento tiene que ser numérico");
	        }
	    }
	}

	@FXML private Button btnDatosDescartar;
	@FXML public void descartarPersona(ActionEvent event) {
	    datosMostrarPersona();
	}

	@FXML private TextField txtDatosApellido;
	@FXML private TextField txtDatosNombre;
	@FXML private ComboBox<TipoDocumento> cmbDatosTipoDocumento;
	@FXML private TextField txtDatosDocumento;
	@FXML private DatePicker dtpDatosFechaNacimiento;


// --------------------------- Pestaña Contactos ---------------------------- //

	@FXML private Button btnContactosNuevo;
	@FXML public void nuevoContacto(ActionEvent event) {

	}

	@FXML private Button btnContactosGuardar;
	@FXML public void guardarContacto(ActionEvent event) {

	}

	@FXML private Button btnContactosDescartar;
	@FXML public void descartarContacto(ActionEvent event) {

	}

	@FXML private Button btnContactosEliminar;
	@FXML public void eliminarContacto(ActionEvent event) {

	}

	@FXML private TableView<?> tblContactos;
	@FXML private TableColumn<?, ?> colContactosTipo;
	@FXML private TableColumn<?, ?> colContactosDato;

	@FXML private ComboBox<?> cmbContactosTipo;
	@FXML private TextField txtContactosDato;

// -------------------------- Pestaña Domicilios ---------------------------- //

	@FXML private Button btnDomiciliosNuevo;
	@FXML public void nuevoDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomiciliosGuardar;
	@FXML public void guardarDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomicilioDescartar;
	@FXML public void descartarDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomiciliosEliminar;
	@FXML public void eliminarDomicilio(ActionEvent event) {

	}

	@FXML private TableView<?> tblDomicilios;
	@FXML private TableColumn<?, ?> colDomiciliosProvincia;
	@FXML private TableColumn<?, ?> colDomiciliosCiudad;
	@FXML private TableColumn<?, ?> colDomiciliosCP;
	@FXML private TableColumn<?, ?> colDomiciliosDireccion;

	@FXML private ComboBox<?> cmbDomiciliosProvincia;
	@FXML private TextField txtDomiciliosCiudad;
	@FXML private TextField txtDomiciliosCP;
	@FXML private TextField txtDomiciliosDireccion;

// ---------------------------- Pestaña Títulos ----------------------------- //

	@FXML private TextField txtTitulosTitulo;

	@FXML private Button btnTitulosAgregar;
	@FXML public void agregarTitulo(ActionEvent event) {

	}

	@FXML private Button btnTitulosQuitar;
	@FXML public void quitarTitulo(ActionEvent event) {

	}

	@FXML private TableView<?> tblTitulos;
	@FXML private TableColumn<?, ?> colTitulosTitulo;
	@FXML private TableColumn<?, ?> colTituloMayor;

}
