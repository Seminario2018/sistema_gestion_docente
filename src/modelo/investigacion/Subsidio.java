package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;

public class Subsidio {
	private LocalDate fecha; // Solo año
	private String disposicion;
	private float montoTotal;
	private String observaciones;
	
	private ArrayList<IRendicion> rendiciones;
}