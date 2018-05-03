package vista;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class Pantalla extends Application {

	private static final String WINDOW_POSITION_X = "Window_Position_X";
	private static final String WINDOW_POSITION_Y = "Window_Position_Y";
	
	private static final double DEFAULT_X = 10;
	private static final double DEFAULT_Y = 10;
	
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";
	

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
			Parent root = FXMLLoader.load(getClass().getResource(this.fxmlURL));

			if (this.sinDecorar) {
				stage.initStyle(StageStyle.UNDECORATED);
			}

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.show();

			// Cargar el tamaño de ventana anterior 
			Preferences pref = Preferences.userRoot().node(this.fxmlURL);
			
			double x = pref.getDouble(WINDOW_POSITION_X, DEFAULT_X);
			double y = pref.getDouble(WINDOW_POSITION_Y, DEFAULT_Y);
			
			stage.setX(x);
			stage.setY(y);
			
			double width = pref.getDouble(WINDOW_WIDTH, 0);
			double height = pref.getDouble(WINDOW_HEIGHT, 0);
			
			if (width != 0) {
				stage.setWidth(width);
			}
			
			if (height != 0) {
				stage.setHeight(height);
			}
 

			// Guardar el tamaño de ventana
			stage.setOnCloseRequest((final WindowEvent event) -> {
				Preferences preferences = Preferences.userRoot().node(this.fxmlURL);
				preferences.putDouble(WINDOW_POSITION_X, stage.getX());
				preferences.putDouble(WINDOW_POSITION_Y, stage.getY());
				preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
				preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
			});

		} else {
			throw new IOException("No se ha definido la URL de la pantalla.");
		}
	}

	public String getFxmlURL() {
		return fxmlURL;
	}

	public void setFxmlURL(String fxmlURL) {
		this.fxmlURL = fxmlURL;
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
