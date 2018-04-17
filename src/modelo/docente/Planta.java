package modelo.docente;

import java.time.LocalDate;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;

public class Planta {
	private IArea area;
	private ICargo cargo;
	private TipoCargo tipoCargo;
	private String disposicion;
	private LocalDate dispDesde;
	private LocalDate dispHasta;
	private float ultimoCosto;
	private LocalDate fechaUltCost;
	private String resolucion;
	private LocalDate resDesde;
	private LocalDate resHasta;
	private EstadoCargo estado;
}