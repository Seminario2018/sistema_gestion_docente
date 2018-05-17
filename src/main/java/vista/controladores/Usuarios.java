package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Usuarios implements Initializable {

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
// -------------------------------- General --------------------------------- //

	@FXML private TextField txtDocumento;
	@FXML private TextField txtNombre;

	@FXML private Button btnSeleccionarPersona;
	@FXML public void seleccionarPersona(ActionEvent event) {
		
	}
	
	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtContrasena;
	@FXML private PasswordField txtConfirmar;
	@FXML private TextField txtDescripcion;
	
	@FXML private Button btnBuscar;
	@FXML public void buscarUsuario(ActionEvent event) {
		
	}
	
	@FXML private Button btnNuevo;
	@FXML public void nuevoUsuario(ActionEvent event) {
		
	}
	
	@FXML private Button btnGuardar;
	@FXML public void guardarUsuario(ActionEvent event) {
		
	}
	
	@FXML private Button btnDescartar;
	@FXML public void descartarUsuario(ActionEvent event) {
		
	}
	
	@FXML private Button btnEliminar;
	@FXML public void eliminarUsuario(ActionEvent event) {
		
	}

	
// --------------------------------- Roles ---------------------------------- //
	
	@FXML private Button btnAgregar;
	@FXML public void agregarRol(ActionEvent event) {
		
	}
	
	@FXML private Button btnQuitar;
	@FXML public void quitarRol(ActionEvent event) {
		
	}
	
	@FXML private TableView<?> tblDisponibles;
	@FXML private TableColumn<?, ?> colDisponibles; // Descripción del rol
	
	@FXML private TableView<?> tblUsuario;
	@FXML private TableColumn<?, ?> colUsuario; // Descripción del rol
	
}


