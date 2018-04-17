package modelo.investigacion;

import java.time.LocalDate;
import java.util.ArrayList;

import modelo.auxiliares.EstadoProyecto;
import modelo.docente.IDocente;

public class Proyecto {
	private int id;
	private String nombre;
	private String resumen;
	private LocalDate fechaPresentacion;
	private LocalDate fechaAprobacion;
	private String disposicion;
	private IDocente director;
	private IDocente codirector;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private EstadoProyecto estado;
	
	private ArrayList<IIntegrante> integrantes;
	private ArrayList<ISubsidio> subsidios;
	private ArrayList<IProrroga> prorrogas;
}