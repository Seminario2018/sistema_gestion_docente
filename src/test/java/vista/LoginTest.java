package vista;

import org.junit.Test;

import javafx.application.Application;


/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class LoginTest {
	
	@Test
	public void prueba() {
		try {
			Application.launch(Pantalla.class, "Login", "Ingresar"
					,"false"
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
