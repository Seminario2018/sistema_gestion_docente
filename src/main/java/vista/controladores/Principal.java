package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
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

	public void setMensajeEstado(String mensaje) {
		if (mensaje != null && !mensaje.equals("")) {
			this.lblMensajes.setText(mensaje);
		}
	}
	
	@Override
	public void inicializar() {
		for (IRol rol : this.usuario.getRoles()) {
			for (IPermiso p : rol.getPermisos()) {
				if (p.getListar())
					switch (p.getModulo()) {
					case USUARIOS:
						this.mnuUsuarios.setDisable(false);
						break;
					case ROLES:
						this.mnuRoles.setDisable(false);
						break;
					case PERSONAS:
						this.mnuPersonas.setDisable(false);
						break;
					case DOCENTES:
						this.mnuDocentes.setDisable(false);
						break;
					case PROGRAMAS:
						this.mnuProgramas.setDisable(false);
						break;
					case PROYECTOS:
						this.mnuProyectos.setDisable(false);
						break;
					case INFORMES:
						this.mnuInformes.setDisable(false);
						break;
					default:
					}
			}
		}
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
    	this.gestorPantalla.lanzarPantalla("Roles", null);
    }

    @FXML
    void pantallaUsuarios(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("Usuarios", null);
    }

    @FXML
    void pantallaPersonas(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("Personas", null);
    }

    @FXML
    void pantallaDocentes(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("Docentes", null);
    }

    @FXML
    void pantallaProgramas(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("Programas", null);
    }

    @FXML
    void pantallaProyectos(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("Proyectos", null);
    }

    @FXML
    void pantallaListaInformes(ActionEvent event) {
    	this.gestorPantalla.lanzarPantalla("ListaInformes", null);
    }
		
}
