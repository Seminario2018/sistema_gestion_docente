package vista.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de set. de 2018
 */
public class ConfigCon extends ControladorVista implements Initializable {
	
	@FXML private TextField txtDriver;
	@FXML private TextField txtUsuario;
	@FXML private PasswordField pwdContrasena;
	@FXML private TextField txtURL;	

	@FXML private Button btnProbar;
	@FXML void probarConexion(ActionEvent event) {
		
	}
    @FXML private Button btnAceptar;
    @FXML void aceptar(ActionEvent event) {
    	
    }
    @FXML private Button btnCancelar;
    @FXML void cancelar(ActionEvent event) {
    	
    }
}