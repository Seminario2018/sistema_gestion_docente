package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Personas implements Initializable {

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
// -------------------------------- General --------------------------------- //
	
	@FXML private TextField txtPersonasDocumento;
	@FXML private TextField txtPersonasNombre;
	
	@FXML private Button btnPersonasBuscar;
	@FXML public void buscarPersona(ActionEvent event) {
		
	}
	
	@FXML private Button btnPersonasNueva;
	@FXML public void nuevaPersona(ActionEvent event) {
		
	}
	
	@FXML private Button btnPersonasEliminar;
	@FXML public void eliminarPersona(ActionEvent event) {
		
	}
	
// ----------------------------- Pestaña Datos ------------------------------ //

	@FXML private Button btnDatosGuardar;
	@FXML public void guardarPersona(ActionEvent event) {
		
	}
	
	@FXML private Button btnDatosDescartar;
	@FXML public void descartarPersona(ActionEvent event) {
		
	}
	
	@FXML private TextField txtDatosApellido;
	@FXML private TextField txtDatosNombre;
	@FXML private ComboBox<?> cmbDatosTipoDocumento;
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
