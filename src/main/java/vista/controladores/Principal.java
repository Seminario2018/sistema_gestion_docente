package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.Initializable;
import vista.GestorPantalla;

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
    private AnchorPane mainPane;

    @FXML
    private MenuItem mnuDocentes;

    @FXML 
    void pantallaDocentes() {
    	this.gestor.lanzarPantalla("Docentes", null);
    }
	
}
