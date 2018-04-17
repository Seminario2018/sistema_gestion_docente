package controlador;

import modelo.usuario.Modulo;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class Main {

	public static void main(String[] args) {
		for(Modulo m: Modulo.values()) {
			System.out.println(m.toString());
		}
	}

}
