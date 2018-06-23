package controlador;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import modelo.auxiliares.Filtro;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Modulo;
import modelo.usuario.Permiso;
import modelo.usuario.Rol;
import vista.GestorPantalla;
import vista.controladores.ControladorVista;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class Main extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
//		setUserAgentStylesheet(STYLESHEET_CASPIAN);
		GestorPantalla gp = new GestorPantalla();

//		/* TEST Usuario hardcodeado *
		ControlUsuario controlUsuario = new ControlUsuario(null);
		IUsuario usuario = controlUsuario.getIUsuario();
		usuario.setUser("pepe");
		usuario.setPass("123456");
		usuario.setGrupos(new ArrayList<IRol>());

		IRol r = new Rol(0, "su");
		for (Modulo modulo : Modulo.values()) {
		    IPermiso permiso = new Permiso(modulo);
		    permiso.setCrear(true);
	        permiso.setEliminar(true);
	        permiso.setModificar(true);
	        permiso.setListar(true);
	        r.agregarPermiso(permiso);
		}
		usuario.agregarRol(r);
		
				
		gp.lanzarPantallaPrincipal(usuario);
//		*/
//		gp.lanzarPantallaLogin();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
