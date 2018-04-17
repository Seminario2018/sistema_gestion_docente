package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public class Division {
	private int codigo;
	private String descripcion;
	private IDocente jefe;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
}