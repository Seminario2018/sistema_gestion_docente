package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.Window;
import modelo.usuario.IUsuario;
import vista.controladores.ControladorVista;
import vista.controladores.Login;
import vista.controladores.Principal;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de may. de 2018
 */
public class GestorPantalla {

	// Utilizadas para guardar las preferencias de ventana
	private static final String WINDOW_POSITION_X = "Window_Position_X";
	private static final String WINDOW_POSITION_Y = "Window_Position_Y";
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";

	// Utilizadas para arrastrar ventanas sin decorar
	private double posX;
	private double posY;

	private static final String ID_MAIN_PANE = "mainPane";

	Stage loginStage;
	Stage primaryStage;

	IUsuario usuario;
	Principal pantallaPrincipal;
	Pane internalPane;
	Map<String, ControladorVista> controladoresActivos = new HashMap<String, ControladorVista>();
	Map<String, Window> pantallasAbiertas = new HashMap<String, Window>();
	Map<String, List<Window>> pantallasHijas = new HashMap<String, List<Window>>();
	
	public static final String KEY_PADRE = "padre";

	public void lanzarPantallaLogin() {
		this.loginStage = new Stage();
		try {
			FXMLLoader loader = getLoader(Login.TITULO);
			Parent root = loader.load();

			Login controlador = (Login) loader.getController();
			controlador.setGestorPantalla(this);
		
			this.loginStage.initStyle(StageStyle.UNDECORATED);
			permitirArrastrar(this.loginStage, loader);
			
			Scene scene = new Scene(root);
			this.loginStage.setScene(scene);
			this.loginStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarPantallaLogin() {
		this.loginStage.close();
	}

	public void lanzarPantallaPrincipal(IUsuario usuario) {

			this.usuario = usuario;
			this.primaryStage = new Stage();
			this.primaryStage.setTitle("Plumas 2 - Sistema de Gestión Docente - Universidad Nacional de Luján");

		try {
			FXMLLoader loader = getLoader("Principal");
			Parent root = loader.load();

			this.pantallaPrincipal = loader.getController();
			this.pantallaPrincipal.setGestorPantalla(this);
			this.pantallaPrincipal.setUsuario(this.usuario);
			this.pantallaPrincipal.setLabelUsuario(this.usuario.getUser());
			this.internalPane = (Pane) loader.getNamespace().get(ID_MAIN_PANE);

			Scene scene = new Scene(root);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			preferenciasVentana("Principal");
			this.pantallaPrincipal.inicializar();
			this.primaryStage.setMaximized(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void lanzarPantalla(String nombre, Map<String, Object> args) {
		try {
			// La pantalla ya existe y hay que traerla al frente
			if (pantallasAbiertas.get(nombre) != null) {
				pantallasAbiertas.get(nombre).toFront();
				pantallasAbiertas.get(nombre).requestFocus();

			} else {
				FXMLLoader loader;
				// Si se trata de una pantalla modal
				if (nombre.contains(" ")) {
					loader = getLoader(nombre.substring(0, nombre.indexOf(" ")));
				} else {
					loader = getLoader(nombre);
				}

				Parent root = loader.load();

				Window window = new Window();
				window.getContentPane().getChildren().add(root);
				preferenciasVentana(window, nombre);
				// Problemas con la GUI
				window.setResizableWindow(false);

				// Tomar el tamaño de la región que va a contener (por lo general 800x600)
				window.setPrefWidth(((Region) root).getPrefWidth());
				window.setPrefHeight(((Region) root).getPrefHeight());

				// Nombre y botones de la barra de título
				window.setTitle(nombre);
				window.getRightIcons().addAll(
//						new MinimizeIcon(window),
						new CloseIcon(window)
						);

				// Cuando la ventana se cierra, quitarla de la lista de pantallas abiertas
				window.setOnClosedAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						pantallaCerrada(nombre);
					}
				});

				Object controlador = loader.getController();
				((ControladorVista) controlador).setGestorPantalla(this);
				((ControladorVista) controlador).setWindow(window);
				((ControladorVista) controlador).setUsuario(this.usuario);
				((ControladorVista) controlador).inicializar();

				this.controladoresActivos.put(nombre, (ControladorVista) controlador);
				this.pantallasAbiertas.put(nombre, window);

				// Una pantalla está llamando a otra
				if (args != null && !args.isEmpty()) {
					Object oPadre = args.get(GestorPantalla.KEY_PADRE);
					if (oPadre != null && oPadre instanceof String) {
						String padre = (String) oPadre;
						List<Window> hijas = this.pantallasHijas.get(padre);
						if (hijas == null) {
							this.pantallasHijas.put(padre, new ArrayList<Window>());
						}
						this.pantallasHijas.get(padre).add(window);
					}

					if (controlador instanceof ControladorVista) {
                        ((ControladorVista) controlador).recibirParametros(args);
                    }
				}


				this.internalPane.getChildren().add(window);
				window.requestFocus();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*

		if (this.sinDecorar) {
			stage.initStyle(StageStyle.UNDECORATED);
			permitirArrastrar(stage, loader);
		}

		stage.setTitle(this.titulo);
		stage.setScene(scene);
		stage.show();

		preferenciasVentana(stage);

		*/
	}

	/**
	 * Cerrar una pantalla
	 * @param nombre el nombre de la pantalla a cerrar
	 */
	public void cerrarPantalla(String nombre) {
		Window w = this.pantallasAbiertas.get(nombre);
		if (w != null) {
			pantallaCerrada(nombre);
			w.close();
		}
	}

	/**
	 * Se ejecuta al cerrar una pantalla, e.g. la pantalla de Búsqueda luego de
	 * seleccionar una fila o la pantalla de Búsqueda de una pantalla que ya no existe,
	 * e.g. se cierra Docente con la pantalla "Busqueda Persona" abierta.
	 *
	 * @param nombre el nombre de la pantalla
	 */
	public void pantallaCerrada(String nombre) {
		this.controladoresActivos.remove(nombre);
		this.pantallasAbiertas.remove(nombre);
		// Cerrar las pantallas hijas
		List<Window> hijas = this.pantallasHijas.get(nombre);
		if (hijas != null) {
			for (Window hija : hijas) {
				hija.close();
			}
		}
		if (this.internalPane.getChildren().size() > 0) {
			this.internalPane.getChildren().get(
					this.internalPane.getChildren().size() - 1)
				.requestFocus();
		}
	}

	/**
	 * Escribir un mensaje en la parte inferior de la pantalla
	 */
	public void mensajeEstado(String mensaje) {
		this.pantallaPrincipal.setMensajeEstado(mensaje);
	}


	/**
	 * Agregar manejadores para permitir arrastrar la ventana sin decoración
	 * @param stage - el Stage a modificar.
	 * @param loader - el FXMLLoader para obtener el mainPane.
	 */
	public void permitirArrastrar(Stage stage, FXMLLoader loader) {

		Node mainPane = (Node) loader.getNamespace().get(ID_MAIN_PANE);

		mainPane.setOnMousePressed((MouseEvent mouseEvent) -> {
			this.posX = mainPane.getScene().getWindow().getX() - mouseEvent.getScreenX();
			this.posY = mainPane.getScene().getWindow().getY() - mouseEvent.getScreenY();
		});

		mainPane.setOnMouseDragged((MouseEvent mouseEvent) -> {
			stage.setX(mouseEvent.getScreenX() + this.posX);
			stage.setY(mouseEvent.getScreenY() + this.posY);
		});
	}

	/**
	 * Carga y permite guardar los cambios realizados a la ventana,
	 * i.e. agrandar, achicar y mover la ventana.
	 * @param window - el Window a modificar.
	 */
	private void preferenciasVentana(Window window, String name) {

		// Cargar el tamaño de ventana anterior
		Preferences pref = Preferences.userRoot().node(name);

		double width = pref.getDouble(WINDOW_WIDTH, -1);
		double height = pref.getDouble(WINDOW_HEIGHT, -1);

		if (width != -1) {
			window.setPrefWidth(width);
		}

		if (height != -1) {
			window.setPrefHeight(height);
		}


		double x = pref.getDouble(WINDOW_POSITION_X, -1);
		double y = pref.getDouble(WINDOW_POSITION_Y, -1);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

		if (x != -1) {
			window.setLayoutX(x);
		} else {
			window.setLayoutX((primScreenBounds.getWidth() - window.getWidth()) / 2);
		}

		if (y != -1) {
			window.setLayoutY(y);
		} else {
			window.setLayoutY((primScreenBounds.getHeight() - window.getHeight()) / 2);
		}

		// Guardar el tamaño de ventana
		window.setOnCloseAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Preferences preferences = Preferences.userRoot().node(name);
				preferences.putDouble(WINDOW_POSITION_X, window.getLayoutX());
				preferences.putDouble(WINDOW_POSITION_Y, window.getLayoutY());
				preferences.putDouble(WINDOW_WIDTH, window.getPrefWidth());
				preferences.putDouble(WINDOW_HEIGHT, window.getPrefHeight());
			}
		});
	}


	/**
	 * Carga y permite guardar los cambios realizados a la ventana,
	 * i.e. agrandar, achicar y mover la ventana.
	 * @param stage - el Stage a modificar.
	 */
	private void preferenciasVentana(String name) {

		// Cargar el tamaño de ventana anterior
		Preferences pref = Preferences.userRoot().node(name);

		double width = pref.getDouble(WINDOW_WIDTH, -1);
		double height = pref.getDouble(WINDOW_HEIGHT, -1);

		if (width != -1) {
			this.primaryStage.setWidth(width);
		}

		if (height != -1) {
			this.primaryStage.setHeight(height);
		}


		double x = pref.getDouble(WINDOW_POSITION_X, -1);
		double y = pref.getDouble(WINDOW_POSITION_Y, -1);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

		if (x != -1) {
			this.primaryStage.setX(x);
		} else {
			this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2);
		}

		if (y != -1) {
			this.primaryStage.setY(y);
		} else {
			this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 2);
		}


		// Guardar el tamaño de ventana
		this.primaryStage.setOnCloseRequest((final WindowEvent event) -> {
			Preferences preferences = Preferences.userRoot().node(name);
			preferences.putDouble(WINDOW_POSITION_X, this.primaryStage.getX());
			preferences.putDouble(WINDOW_POSITION_Y, this.primaryStage.getY());
			preferences.putDouble(WINDOW_WIDTH, this.primaryStage.getWidth());
			preferences.putDouble(WINDOW_HEIGHT, this.primaryStage.getHeight());
		});
	}

	private FXMLLoader getLoader(String nombre) throws IOException {
		String fxmlURL = "interfaces/" + nombre + ".fxml";

		if (fxmlURL != null && !fxmlURL.equals("")) {
			return new FXMLLoader(getClass().getResource(fxmlURL));

		} else {
			throw new IOException("No se ha definido la URL de la pantalla.");
		}
	}

	public IUsuario getUsuario() {
	    return this.usuario;
	}
}
