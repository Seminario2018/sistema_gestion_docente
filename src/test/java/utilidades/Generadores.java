package utilidades;

import java.util.Random;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 19 de may. de 2018
 */
public class Generadores {
	
	public static void main(String[] args) {
		generarDocentes(38);
	}
	
	private static void generarDocentes(int cantidad) {
		String[] nombres = {
				"Cristina",
				"José",
				"María",
				"Isabel",
				"Julián",
				"Ángeles",
				"Ana",
				"Jorge",
				"Carlos",
				"Vicente",
				"Pilar",
				"Nuria",
				"Antonio",
				"Carmen",
				"Dolores",
				"Rosario",
				"Óscar",
				"Laura",
				"Francisco",
				"Antonia",
				"Ramón",
				"Juan",
				"Javier",
				"Guillermo",
				"Rosa",
				"Martín",
				"Nicolás",
				"Leonardo",
				"Elías",
				"Tomás",
				"Ezequiel",
				"Adán"
				
		};
		
		String[] apellidos = {
			"Lozano",
			"Cabeza",
			"Miranda",
			"Del Moral",
			"Peralta",
			"Castaño",
			"Vázquez",
			"Gutiérrez",
			"López",
			"Iborra",
			"González",
			"Algaba",
			"Lafuente",
			"Mate",
			"Álvarez",
			"Fernández",
			"Herrero",
			"Martínez",
			"Pérez",
			"García",
			"Sánchez",
			"Muñoz",
			"Cabo",
			"Sainz",
			"Rubio",
			"San Juan",
			"Manzano",
			"Lopes",
			"Benítez",
			"Montserrat"

		};
		
		int min = 1945;
		int max = 1975;
		
		Random rnd = new Random();

		String personas = "";
		String docentes = "";
		
		for (int i = 0; i < cantidad; i++) {
			String apellido = apellidos[rnd.nextInt(apellidos.length)];
			if (rnd.nextBoolean()) {
				String apellido2 = apellido;
				while (apellido.equals(apellido2))
					apellido2 = apellidos[rnd.nextInt(apellidos.length)];
				
				apellido += " " + apellido2;
			}
			
			String nombre = nombres[rnd.nextInt(nombres.length)];
			if (rnd.nextBoolean()) {
				String nombre2 = nombre;
				while (nombre.equals(nombre2))
					nombre2 = nombres[rnd.nextInt(nombres.length)];
				
				nombre += " " + nombre2;
			}
			
			int[] fecha = generarFecha(min, max);
			
			String anio = String.valueOf(fecha[0]);
			String mes = String.valueOf(fecha[1]);
			if (fecha[1] < 10)
				mes = "0" + mes;
			String dia = String.valueOf(fecha[2]);
			if (fecha[2] < 10)
				dia = "0" + dia;
			
			int dni = 9000000 + ((fecha[0] - min) / 3) * 1000000 + rnd.nextInt(1000000);
			
			int legajo = 3000 + rnd.nextInt(100) - (max - fecha[0]) * 100; 

			personas +=
					"(0, " 
					+ dni
					+ ", '"
					+ apellido
					+ "', '"
					+ nombre
					+ "', '"
					+ anio + "-" + mes + "-" + dia
					+ "', 0),\n";
			
			
			docentes +=
					"("
					+ legajo
					+ ", 0, "
					+ dni
					+ ", 'Observaciones de "
					+ apellido
					+ "', "
					+ rnd.nextInt(6) // Categoría de investigación
					+ ", "
					+ rnd.nextInt(2) // Estado
					+ "),\n";
			
		}
		
		System.out.println(personas + "\n\n" + docentes);
	}
	
	
	private static int[] generarFecha(int min, int max) {
		Random rnd = new Random();
		
		String meses30 = "4 6 9 11";
		
		int anio = rnd.nextInt(max - min + 1) + min;
		int mes = rnd.nextInt(12) + 1;
	
		int dia = rnd.nextInt(31) + 1;
				
		if (meses30.contains(String.valueOf(mes))) {
			dia = rnd.nextInt(30) + 1;
		}
		
		if (mes == 2) {
			if ((anio % 4 == 0 && !(anio % 100 == 0))
					|| anio % 1000 == 0) {
				
				dia = rnd.nextInt(29) + 1;	
			} else {
				dia = rnd.nextInt(28) + 1;	
			}
		}
		
		int[] fecha = new int[3];
		fecha[0] = anio;
		fecha[1] = mes;
		fecha[2] = dia;
		
		return fecha;
	}
	
}
