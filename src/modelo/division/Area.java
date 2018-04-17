package modelo.division;

import java.time.LocalDate;

import modelo.docente.IDocente;

public class Area {
	private int codigo;
	private String descripcion;
	private IDivision division;
	private IDocente responsable;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
	private IArea subAreaDe;
}