package vista;

import java.io.IOException;
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
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";
	private static final double DEFAULT_X = 10;
	private static final double DEFAULT_Y = 10;
	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 600;
	private static final String NODE_NAME = "Main";

	private String fxmlURL;

	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws IOException {
		
		Parameters params = getParameters();
		setFxmlURL(params.getRaw().get(0));
		
		if (this.fxmlURL != null && !this.fxmlURL.equals("")) {
			Parent root = FXMLLoader.load(getClass().getResource(this.fxmlURL));

			if (fxmlURL.equals("Login.xml")) {
				stage.initStyle(StageStyle.UNDECORATED);
			}

			Scene scene = new Scene(root);

			stage.setScene(scene);
			stage.show();

			/* Cargar el tamaño de ventana anterior */
			Preferences pref = Preferences.userRoot().node(NODE_NAME);
			double x = pref.getDouble(WINDOW_POSITION_X, DEFAULT_X);
			double y = pref.getDouble(WINDOW_POSITION_Y, DEFAULT_Y);
			double width = pref.getDouble(WINDOW_WIDTH, DEFAULT_WIDTH);
			double height = pref.getDouble(WINDOW_HEIGHT, DEFAULT_HEIGHT);
			stage.setX(x);
			stage.setY(y);
			stage.setWidth(width);
			stage.setHeight(height);

			/* Guardar el tamaño de ventana */
			stage.setOnCloseRequest((final WindowEvent event) -> {
				Preferences preferences = Preferences.userRoot().node(NODE_NAME);
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

}
