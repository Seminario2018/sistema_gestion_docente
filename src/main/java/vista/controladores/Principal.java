package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.fxml.Initializable;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Principal extends ControladorVista implements Initializable {
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
    private MenuItem mnuUsuarios;

    @FXML
    private MenuItem mnuPersonas;

    @FXML
    private Menu mnuInformes;

    @FXML
    private Label lblMensajes;

    @FXML
    private MenuItem mnuRoles;

    @FXML
    private MenuItem mnuProyectos;

    @FXML
    private StackPane mainPane;

    @FXML
    private MenuItem mnuDocentes;

    @FXML
    private MenuItem mnuProgramas;

    @FXML
    void pantallaRoles(ActionEvent event) {

    }

    @FXML
    void pantallaUsuarios(ActionEvent event) {

    }

    @FXML
    void pantallaPersonas(ActionEvent event) {

    }

    @FXML
    void pantallaDocentes(ActionEvent event) {
    	this.gestor.lanzarPantalla("Docentes", null);
    }

    @FXML
    void pantallaProgramas(ActionEvent event) {

    }

    @FXML
    void pantallaProyectos(ActionEvent event) {
    	this.gestor.lanzarPantalla("Proyectos", null);
    }

    @FXML
    void pantallaListaInformes(ActionEvent event) {

    }
		
}
