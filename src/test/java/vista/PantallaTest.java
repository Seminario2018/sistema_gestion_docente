package vista;

import org.junit.Test;

import javafx.application.Application;


/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class PantallaTest {
	
	@Test
	public void prueba() {
		String name = "Login";
		String titulo = name;
		
		try {
			if (name.equals("Login")) {
				Application.launch(Pantalla.class, "Login", "Ingreso", "false");
			} else {
				Application.launch(Pantalla.class, name, titulo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
