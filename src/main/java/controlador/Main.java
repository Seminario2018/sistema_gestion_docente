package controlador;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Modulo;
import modelo.usuario.Permiso;
import modelo.usuario.Rol;
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
//		setUserAgentStylesheet(STYLESHEET_CASPIAN);

		GestorPantalla gp = new GestorPantalla();
		ControlUsuario controlUsuario = new ControlUsuario(null);
		IUsuario usuario = controlUsuario.getIUsuario();
		usuario.setUser("pepe");
		usuario.setPass("123456");
		usuario.setGrupos(new ArrayList<IRol>());

		Rol r = new Rol(0, "su");
		Permiso per  = new Permiso(Modulo.USUARIOS);
		per.setCrear(true);
		per.setEliminar(true);
		per.setModificar(true);
		per.setListar(true);
		r.agregarPermiso(per);

		usuario.agregarGrupo(r);

//		gp.lanzarPantallaPrincipal(new Usuario("pepe", "pepe", null, null));
		gp.lanzarPantallaPrincipal(usuario);
	}


	public static void main(String[] args) {
		launch(args);
	}
}
