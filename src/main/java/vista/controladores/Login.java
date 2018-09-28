package vista.controladores;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controlador.ControlUsuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import modelo.auxiliares.hash.PasswordUtil;
import modelo.usuario.IUsuario;

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
		
		try {
			Image logo = new Image(getClass()
					.getResource("/vista/interfaces/logoPlumas.png")
					.toString());
			
			BackgroundImage bgi = new BackgroundImage(logo,
					BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.DEFAULT,
					BackgroundSize.DEFAULT);
			
			mainPane.setBackground(new Background(bgi));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ControlUsuario control = new ControlUsuario(this);

	@FXML private TextField txtLoginUsuario;

	@FXML private Hyperlink lnkLoginAyuda;
	@FXML void manualUsuarios(ActionEvent event) {
		if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
		    try {
				Desktop.getDesktop().browse(new URI("https://drive.google.com/open?id=1R3yBj3QhcbKckm97Z389lqZFrshZVPfh"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			dialogoConfirmacion("Manual de Usuario", "Sistema no soportado",
					"Su Sistema Operativo no soporta la navegación en línea"
					+ " del Manual de Usuario. Puede encontrar una copia en"
					+ " la carpeta donde instaló este programa.");
		}
    }

	@FXML private TextField txtLoginContrasena;

	@FXML private Button btnLoginSalir;
	@FXML void salir(ActionEvent event) {
		this.gestorPantalla.cerrarPantallaLogin();
	}

	@FXML private GridPane mainPane;

	@FXML private Button btnLoginIngresar;
	@FXML void ingresar(ActionEvent event) {
		String encabezado = "Iniciar Sesión";
		String contenido = "El usuario y/o contraseña son inválidos";
		
		String nombre = this.txtLoginUsuario.getText();
		if (nombre == null || "".equals(nombre)) {
			alertaError(TITULO, encabezado, "El campo \"Usuario\" es obligatorio");
            return;
		}
		
		String contrasena = this.txtLoginContrasena.getText();
        if (contrasena == null || "".equals(contrasena)) {
        	alertaError(TITULO, encabezado, "El campo \"Contraseña\" es obligatorio");
            return;
        }
        
		IUsuario usuarioSelect = control.getIUsuario();
        usuarioSelect.setUser(nombre);

        List<IUsuario> listaUsuarios = control.listarUsuario(usuarioSelect);
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            alertaError(TITULO, encabezado, contenido);
            return;
        }
        
        IUsuario usuario = listaUsuarios.get(0);
        if (PasswordUtil.ValidatePass(contrasena,
        		usuario.getHash().getHash(), usuario.getHash().getSalt())) {
        	this.gestorPantalla.lanzarPantallaPrincipal(listaUsuarios.get(0));
        	this.gestorPantalla.cerrarPantallaLogin();        	
        } else {
        	alertaError(TITULO, encabezado, contenido);
        }
        
        
        
		
		
		
	    /* TEST Usuarios hardcodeados */
		/*
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
		*/
	}

}
