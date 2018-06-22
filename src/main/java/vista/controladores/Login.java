package vista.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import controlador.ControlUsuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Modulo;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class Login extends ControladorVista implements Initializable {

	public static final String TITULO = "Login";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            txtLoginUsuario.requestFocus();
	        }
	    });
	}

	private ControlUsuario control = new ControlUsuario(this);

	@FXML private TextField txtLoginUsuario;

	@FXML private Label lblLoginAyuda;

	@FXML private TextField txtLoginContrasena;

	@FXML private Button btnLoginSalir;
	@FXML void salir(ActionEvent event) {
		this.gestorPantalla.cerrarPantallaLogin();
	}

	@FXML private GridPane mainPane;

	@FXML private Button btnLoginIngresar;
	@FXML void ingresar(ActionEvent event) {
	    /* TEST Usuarios hardcodeados */
		IUsuario usuario = this.control.getIUsuario();
		IRol rol = this.control.getIRol();
		boolean entra = false;

		if (this.txtLoginUsuario.getText().equals("invitado")) {
			entra = true;
			for (Modulo m : Modulo.values()) {
				if (m != Modulo.USUARIOS && m != Modulo.ROLES) {
					IPermiso p = this.control.getIPermiso();
					p.setModulo(m);
					p.setListar(true);
					rol.agregarPermiso(p);
				}
			}
		}

		if (this.txtLoginUsuario.getText().equals("usuario")) {
			entra = true;
			for (Modulo m : Modulo.values()) {
				if (m != Modulo.USUARIOS && m != Modulo.ROLES) {
					IPermiso p = this.control.getIPermiso();
					p.setModulo(m);
					p.setListar(true);
					p.setCrear(true);
					p.setModificar(true);
					rol.agregarPermiso(p);
				}
			}
		}

		if (this.txtLoginUsuario.getText().equals("admin")) {
			entra = true;
			for (Modulo m : Modulo.values()) {
				IPermiso p = this.control.getIPermiso();
				p.setModulo(m);
				p.setListar(true);
				p.setCrear(true);
				p.setModificar(true);
				p.setEliminar(true);
				rol.agregarPermiso(p);
			}
		}

		if (entra) {
			usuario.agregarRol(rol);
			this.gestorPantalla.lanzarPantallaPrincipal(usuario);
			this.gestorPantalla.cerrarPantallaLogin();
		} else {
			this.alertaError("Ingresar al sistema", "Usuario y contraseña no válidos",
					"El usuario y contraseña ingresados no son válidos.");
		}
		//*/

	    /* TODO Implementar login * /
        IUsuario usuario = control.getIUsuario();
        usuario.setUser(txtLoginUsuario.getText());
        usuario.setPass(txtLoginContrasena.getText());

        List<IUsuario> listaUsuarios = control.listarUsuario(usuario);
        if (listaUsuarios.isEmpty()) {
            alertaError(TITULO, "Iniciar Sesión", "El usuario y/o contraseña son inválidos");
        } else {
            this.gestorPantalla.lanzarPantallaPrincipal(listaUsuarios.get(0));
            this.gestorPantalla.cerrarPantallaLogin();
        }
        //*/
	}

}
