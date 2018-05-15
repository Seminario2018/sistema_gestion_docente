package controlador;

import javafx.application.Application;
import javafx.stage.Stage;
import modelo.usuario.Usuario;
import vista.GestorPantalla;

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
		GestorPantalla gp = new GestorPantalla();
		
		gp.lanzarPantallaPrincipal(new Usuario("pepe", "pepe", null, null));
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
