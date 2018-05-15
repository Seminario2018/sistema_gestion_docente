package vista;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import jfxtras.scene.control.window.Window;
import modelo.usuario.IUsuario;
import vista.controladores.ControladorVista;

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
	private static final String ID_MAIN_PANE = "mainPane";
	private double posX;
	private double posY;
	
	Stage primaryStage;
	
	IUsuario usuario;
	ControladorVista pantallaPrincipal;
	AnchorPane internalPane;
	Map<String, ControladorVista> pantallasAbiertas = new HashMap<String, ControladorVista>();

	public void lanzarPantallaPrincipal(IUsuario usuario) {
		
		try {
			this.usuario = usuario;
			this.primaryStage = new Stage();
			this.primaryStage.setTitle("Plumas 2 - Sistema de Gestión Docente - Universidad Nacional de Luján");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			FXMLLoader loader = getLoader("Principal");
			Parent root = loader.load();
			
			this.pantallaPrincipal = loader.getController();
			this.pantallaPrincipal.setGestor(this);
			this.internalPane = (AnchorPane) loader.getNamespace().get(ID_MAIN_PANE);
			
			Scene scene = new Scene(root);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			preferenciasVentana("Principal");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void lanzarPantalla(String nombre, Map<String, Object> args) {		
		try {
			FXMLLoader loader = getLoader(nombre);
			Parent root = loader.load();
			Window window = new Window();
			window.getContentPane().getChildren().add(root);
			
			this.internalPane.getChildren().add(window);
			
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
	 * Agregar manejadores para permitir arrastrar la ventana sin decoración
	 * @param stage - el Stage a modificar.
	 * @param loader - el FXMLLoader para obtener el mainPane.
	 */
	private void permitirArrastrar(Stage stage, FXMLLoader loader) {
		
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
		/*
		window.setOnCloseRequest((final WindowEvent event) -> {
			Preferences preferences = Preferences.userRoot().node(name);
			preferences.putDouble(WINDOW_POSITION_X, stage.getX());
			preferences.putDouble(WINDOW_POSITION_Y, stage.getY());
			preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
			preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
		});
		*/
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
}
