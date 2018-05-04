package vista;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class Pantalla extends Application {

	// Utilizadas para guardar las preferencias de ventana
	private static final String WINDOW_POSITION_X = "Window_Position_X";
	private static final String WINDOW_POSITION_Y = "Window_Position_Y";
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";
	
	// Utilizadas para arrastrar ventanas sin decorar
	private static final String ID_MAIN_PANE = "mainPane";
	private double posX;
	private double posY;

	private String fxmlURL;
	private String titulo;
	private boolean sinDecorar = false;

	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws IOException {
		
		List<String> params = getParameters().getRaw();
		if (params.size() < 2) {
			throw new IllegalArgumentException(
					"Se deben recibir al menos dos parámetros: fxmlURL y titulo");
		}
		setFxmlURL(params.get(0));
		setTitulo(params.get(1));
		if (params.size() > 2) {
			setSinDecorar(true);
		}
		
		if (this.fxmlURL != null && !this.fxmlURL.equals("")) {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(this.fxmlURL));
			Parent root = loader.load();
			Scene scene = new Scene(root);

			if (this.sinDecorar) {
				stage.initStyle(StageStyle.UNDECORATED);
				permitirArrastrar(stage, loader);
			}
			
			stage.setTitle(this.titulo);
			stage.setScene(scene);
			stage.show();
			
			preferenciasVentana(stage);

		} else {
			throw new IOException("No se ha definido la URL de la pantalla.");
		}
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
	 * @param stage - el Stage a modificar.
	 */
	private void preferenciasVentana(Stage stage) {

		// Cargar el tamaño de ventana anterior 
		Preferences pref = Preferences.userRoot().node(this.fxmlURL);
		
		double width = pref.getDouble(WINDOW_WIDTH, -1);
		double height = pref.getDouble(WINDOW_HEIGHT, -1);
		
		if (width != -1) {
			stage.setWidth(width);
		}
		
		if (height != -1) {
			stage.setHeight(height);
		}
		

		double x = pref.getDouble(WINDOW_POSITION_X, -1);
		double y = pref.getDouble(WINDOW_POSITION_Y, -1);
		
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		
		if (x != -1) {
			stage.setX(x);
		} else {
			stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		}
		
		if (y != -1) {
			stage.setY(y);
		} else {
			stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
		} 
		

		// Guardar el tamaño de ventana
		stage.setOnCloseRequest((final WindowEvent event) -> {
			Preferences preferences = Preferences.userRoot().node(this.fxmlURL);
			preferences.putDouble(WINDOW_POSITION_X, stage.getX());
			preferences.putDouble(WINDOW_POSITION_Y, stage.getY());
			preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
			preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
		});
	}

	public String getFxmlURL() {
		return fxmlURL;
	}

	public void setFxmlURL(String name) {
		if (name != null && !name.equals("")) {
			this.fxmlURL = "interfaces/" + name + ".fxml";
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isSinDecorar() {
		return sinDecorar;
	}

	public void setSinDecorar(boolean sinDecorar) {
		this.sinDecorar = sinDecorar;
	}

}
